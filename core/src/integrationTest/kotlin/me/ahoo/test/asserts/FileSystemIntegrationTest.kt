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

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path

class FileSystemIntegrationTest {

    @TempDir
    lateinit var tempDir: Path

    @Test
    fun `given written file when assert path and file then content is verified`() {
        val path = Files.writeString(tempDir.resolve("greeting.txt"), "hello\nworld")

        path.assert().exists().isRegularFile().isReadable().hasFileName("greeting.txt").hasContent("hello\nworld")
        path.toFile().assert().exists().isFile().hasName("greeting.txt").hasContent("hello\nworld")
        path.toUri().assert().hasScheme("file")
        path.toUri().toURL().assert().hasProtocol("file")
    }

    @Test
    fun `given file lines stream when assert then stream is consumed as list`() {
        val path = Files.writeString(tempDir.resolve("lines.txt"), "a\nb\nc")

        Files.lines(path).use { lines ->
            lines.assert().containsExactly("a", "b", "c")
        }
    }

    @Test
    fun `given missing file when assert exists then AssertJ reports the path`() {
        val missing = tempDir.resolve("missing.txt")

        assertThrownBy<AssertionError> {
            missing.assert().exists()
        }.hasMessageContaining(missing.toString())
    }
}
