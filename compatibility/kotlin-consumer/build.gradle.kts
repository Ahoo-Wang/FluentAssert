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

plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(17)
}

val fluentAssertVersion: String by extra
val fluentAssertRepository: File by extra

// Runs before compilation resolves the library, turning "Could not find me.ahoo.test:fluent-assert-core" into the fix.
val checkFluentAssertRepository by tasks.registering {
    val expected = fluentAssertRepository.resolve("me/ahoo/test/fluent-assert-core/$fluentAssertVersion")
    val version = fluentAssertVersion
    doLast {
        check(expected.resolve("fluent-assert-core-$version.pom").isFile) {
            "FluentAssert $version is not in $expected. Run `./gradlew kotlinConsumerRepository` first; if it used a " +
                "custom build directory or -Pversion, pass the -Pversion and -PfluentAssertRepository values it printed."
        }
    }
}
tasks.named("compileTestKotlin") {
    dependsOn(checkFluentAssertRepository)
}

dependencies {
    // Only the library itself: assertj-core and kotlin-stdlib must arrive through its published POM.
    testImplementation("me.ahoo.test:fluent-assert-core:$fluentAssertVersion")
    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
