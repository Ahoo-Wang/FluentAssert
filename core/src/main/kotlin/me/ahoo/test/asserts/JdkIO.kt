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
 * Returns a [PathAssert] for this path.
 *
 * @sample me.ahoo.test.asserts.samples.JdkIOSamples.path
 */
fun Path?.assert(): PathAssert = PathAssert(this)

/**
 * Returns a [FileAssert] for this file.
 *
 * @sample me.ahoo.test.asserts.samples.JdkIOSamples.file
 */
fun File?.assert(): FileAssert = FileAssert(this)

/**
 * Returns a [UrlAssert] for this URL.
 *
 * @sample me.ahoo.test.asserts.samples.JdkIOSamples.url
 */
fun URL?.assert(): UrlAssert = UrlAssert(this)

/**
 * Returns a [UriAssert] for this URI.
 *
 * @sample me.ahoo.test.asserts.samples.JdkIOSamples.uri
 */
fun URI?.assert(): UriAssert = UriAssert(this)
