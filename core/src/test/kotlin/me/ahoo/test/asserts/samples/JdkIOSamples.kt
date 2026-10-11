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

package me.ahoo.test.asserts.samples

import me.ahoo.test.asserts.assert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path

/**
 * Executable samples rendered into the KDoc of `JdkIO.kt` via `@sample`.
 */
class JdkIOSamples {

    @TempDir
    lateinit var tempDir: Path

    @Test
    fun path() {
        val path = Files.writeString(tempDir.resolve("notes.txt"), "hello")
        path.assert().exists().isRegularFile().hasContent("hello")
    }

    @Test
    fun file() {
        val file = Files.writeString(tempDir.resolve("notes.txt"), "hello").toFile()
        file.assert().exists().isFile().hasName("notes.txt")
    }

    @Test
    fun url() {
        val url = URI("https://example.com/docs").toURL()
        url.assert().hasProtocol("https").hasHost("example.com").hasPath("/docs")
    }

    @Test
    fun uri() {
        val uri = URI("https://example.com/path?query=value")
        uri.assert().hasHost("example.com").hasPath("/path").hasQuery("query=value")
    }
}
