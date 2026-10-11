/*
 * Copyright [2021-present] [ahoo wang <ahoowang@qq.com> (https://github.com/Ahoo-Wang)].
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.ahoo.test.asserts.docs

import me.ahoo.test.asserts.assert
import org.junit.jupiter.api.Test
import java.io.File

/**
 * Keeps hand-written documentation true to the code:
 *
 * - every Kotlin block that uses FluentAssert in the READMEs and the skill docs appears verbatim in a compiled,
 *   executed test ([ReadmeExamples], [SkillExamples]); opt out with a `<!-- not-compiled: reason -->` line
 *   directly above the fence;
 * - every assertion type in the public ABI (`core/api/core.api`) is documented in each mapping table;
 * - install snippets reference the current project version.
 */
class DocumentationTest {

    private val rootDir = File(System.getProperty("fluentassert.rootDir"))
    private val version = System.getProperty("fluentassert.version")
    private val readmes = listOf("README.md", "README.zh-CN.md").map { rootDir.resolve(it) }
    private val fullApi = rootDir.resolve("skills/fluent-assert/references/FULL-API.md")
    private val skillDocs = listOf(rootDir.resolve("skills/fluent-assert/SKILL.md"), fullApi)
    private val docsSourceDir = rootDir.resolve("core/src/test/kotlin/me/ahoo/test/asserts/docs")

    @Test
    fun `README examples are compiled and executed`() {
        assertBlocksCompiled(readmes, docsSourceDir.resolve("ReadmeExamples.kt"))
    }

    @Test
    fun `skill examples are compiled and executed`() {
        assertBlocksCompiled(skillDocs, docsSourceDir.resolve("SkillExamples.kt"))
    }

    @Test
    fun `every public assertion type is documented`() {
        val assertionTypes = rootDir.resolve("core/api/core.api").readLines()
            .mapNotNull { Regex("""Lorg/assertj/core/api/(\w+);$""").find(it)?.groupValues?.get(1) }
            .toSortedSet()
        assertionTypes.assert().isNotEmpty()
        (readmes + fullApi).forEach { doc ->
            val text = doc.readText()
            val undocumented = assertionTypes.filterNot { Regex("""\b$it\b""").containsMatchIn(text) }
            undocumented.assert().describedAs("assertion types missing from %s", doc.name).isEmpty()
        }
    }

    @Test
    fun `install snippets use the project version`() {
        (readmes + fullApi).forEach { doc ->
            val versions = Regex("""fluent-assert-core:([^"']+)|<version>([^<]+)</version>""").findAll(doc.readText())
                .map { it.groupValues.drop(1).first(String::isNotEmpty) }
                .toList()
            versions.assert().describedAs("install versions in %s", doc.name).isNotEmpty().containsOnly(version)
        }
    }

    private fun assertBlocksCompiled(docs: List<File>, examples: File) {
        val source = normalize(examples.readLines())
        docs.forEach { doc ->
            kotlinBlocks(doc).forEach { block ->
                source.windowed(block.size).contains(block)
                    .assert()
                    .describedAs(
                        "%s block starting with '%s' must appear verbatim in %s",
                        doc.name,
                        block.first(),
                        examples.name
                    )
                    .isTrue()
            }
        }
    }

    private fun kotlinBlocks(doc: File): List<List<String>> {
        val lines = doc.readLines()
        val blocks = mutableListOf<List<String>>()
        var index = 0
        while (index < lines.size) {
            if (lines[index].trim() == "```kotlin") {
                val optedOut = index > 0 && lines[index - 1].trim().startsWith(NOT_COMPILED)
                val end = (index + 1 until lines.size).first { lines[it].trim() == "```" }
                val block = normalize(lines.subList(index + 1, end))
                val usesFluentAssert = block.any { it.contains(".assert()") || it.contains("assertThrownBy") }
                if (!optedOut && usesFluentAssert) {
                    blocks += block
                }
                index = end
            }
            index++
        }
        return blocks
    }

    private fun normalize(lines: List<String>): List<String> =
        lines.map(String::trim).filter { it.isNotEmpty() && !it.startsWith("import ") }

    private companion object {
        const val NOT_COMPILED = "<!-- not-compiled"
    }
}
