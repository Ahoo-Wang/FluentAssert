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

package me.ahoo.test.asserts

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.io.File
import java.lang.module.ModuleFinder
import java.lang.reflect.InvocationTargetException
import java.nio.file.Path

/**
 * Loads the jar consumers get on a JPMS module path, next to its runtime dependencies, the way
 * `java --module-path ... --add-modules ALL-MODULE-PATH` does. Classpath consumers are covered by every other test.
 */
class ModulePathIntegrationTest {

    private val libraryJar = Path.of(System.getProperty("fluentassert.libraryJar"))
    private val libraryRuntime = System.getProperty("fluentassert.libraryRuntime")
        .split(File.pathSeparator)
        .filter(String::isNotEmpty)
        .map(Path::of)

    @Test
    fun `given the library jar when found on the module path then it is the automatic module me_ahoo_test_asserts`() {
        val descriptor = ModuleFinder.of(libraryJar).findAll().single().descriptor()

        descriptor.name().assert().isEqualTo(MODULE)
        descriptor.isAutomatic.assert().isTrue()
        descriptor.packages().assert().containsExactly(MODULE)
    }

    @Test
    fun `given a module layer with the library when an extension is called then the AssertJ assertion runs`() {
        val finder = ModuleFinder.of(libraryJar, *libraryRuntime.toTypedArray())
        val boot = ModuleLayer.boot()
        val allModules = finder.findAll().map { it.descriptor().name() }.toSet()
        val configuration = boot.configuration().resolve(finder, ModuleFinder.of(), allModules)
        val layer = boot.defineModulesWithOneLoader(configuration, ClassLoader.getPlatformClassLoader())

        val extensions = layer.findLoader(MODULE).loadClass("$MODULE.JdkKt")
        extensions.module.name.assert().isEqualTo(MODULE)
        val assertion = extensions.getMethod("assert", String::class.java).invoke(null, "Fluent")
        assertion.javaClass.module.name.assert().isEqualTo("org.assertj.core")

        val isEqualTo = assertion.javaClass.getMethod("isEqualTo", Any::class.java)
        isEqualTo.invoke(assertion, "Fluent")
        val failure = assertThrows(InvocationTargetException::class.java) { isEqualTo.invoke(assertion, "Other") }
        failure.targetException.assert().isInstanceOf(AssertionError::class.java).hasMessageContaining("Other")
    }

    private companion object {
        const val MODULE = "me.ahoo.test.asserts"
    }
}
