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

import org.assertj.core.api.FileAssert
import org.assertj.core.api.PathAssert
import org.assertj.core.api.UriAssert
import org.assertj.core.api.UrlAssert
import java.io.File
import java.net.URI
import java.net.URL
import java.nio.file.Path

/**
 * Creates a fluent assertion for Path objects.
 *
 * This extension function provides access to AssertJ's PathAssert methods for fluent testing
 * of file system paths, including null-safe operations and path property verification.
 *
 * Example:
 * ```kotlin
 * val path = Paths.get("/tmp/test.txt")
 * path.assert().exists().isReadable()
 * ```
 *
 * @receiver Path? The path to assert on (nullable)
 * @return PathAssert A fluent assertion object for paths
 */
fun Path?.assert(): PathAssert = PathAssert(this)

/**
 * Creates a fluent assertion for URI objects.
 *
 * This extension function provides access to AssertJ's UriAssert methods for fluent testing
 * of URI objects, including null-safe operations and URI property verification.
 *
 * Example:
 * ```kotlin
 * val uri = URI("https://example.com/path?query=value")
 * uri.assert().hasHost("example.com").hasPath("/path")
 * ```
 *
 * @receiver URI? The URI to assert on (nullable)
 * @return UriAssert A fluent assertion object for URIs
 */
fun File?.assert(): FileAssert = FileAssert(this)

/**
 * Creates a fluent assertion for URL objects.
 *
 * This extension function provides access to AssertJ's UrlAssert methods for fluent testing
 * of URL objects, including null-safe operations and URL property verification.
 *
 * Example:
 * ```kotlin
 * val url = URL("https://example.com")
 * url.assert().hasHost("example.com").hasProtocol("https")
 * ```
 *
 * @receiver URL? The URL to assert on (nullable)
 * @return UrlAssert A fluent assertion object for URLs
 */
fun URL?.assert(): UrlAssert = UrlAssert(this)

/**
 * Creates a fluent assertion for URI objects.
 *
 * This extension function provides access to AssertJ's UriAssert methods for fluent testing
 * of URI objects, including null-safe operations and URI property verification.
 *
 * Example:
 * ```kotlin
 * val uri = URI("https://example.com/path?query=value")
 * uri.assert().hasHost("example.com").hasPath("/path")
 * ```
 *
 * @receiver URI? The URI to assert on (nullable)
 * @return UriAssert A fluent assertion object for URIs
 */
fun URI?.assert(): UriAssert = UriAssert(this)
