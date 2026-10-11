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

// Standalone consumer build: compiles and tests code using FluentAssert with a chosen Kotlin compiler, resolving the
// library from the POM-based repository assembled by the root task `kotlinConsumerRepository`.
//
//   ./gradlew kotlinConsumerRepository
//   ./gradlew -p compatibility/kotlin-consumer test -PkotlinVersion=2.1.21
pluginManagement {
    // Minimum supported consumer Kotlin version (README, REQUIREMENTS.md N2). Raise it only deliberately.
    val kotlinVersion = providers.gradleProperty("kotlinVersion").getOrElse("2.1.21")
    plugins {
        kotlin("jvm") version kotlinVersion
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "kotlin-consumer"

val repoRoot = settingsDir.resolve("../..").canonicalFile

dependencyResolutionManagement {
    // Hermetic resolution: ignore repositories injected by init scripts, and resolve FluentAssert only from the freshly
    // assembled local repository — never from Maven Central or mavenLocal(), which may hold a released build of the
    // same version.
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        exclusiveContent {
            forRepository {
                maven {
                    name = "fluentAssertLocal"
                    url = repoRoot.resolve("build/kotlin-consumer-repo").toURI()
                }
            }
            filter { includeGroup("me.ahoo.test") }
        }
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files(repoRoot.resolve("gradle/libs.versions.toml")))
        }
    }
}

gradle.rootProject {
    extra["fluentAssertVersion"] = java.util.Properties()
        .apply { repoRoot.resolve("gradle.properties").inputStream().use(::load) }
        .getProperty("version")
}
