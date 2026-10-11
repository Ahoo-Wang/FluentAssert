dependencies {
    api("org.assertj:assertj-core")
}

testing {
    suites {
        // End-to-end scenarios against real JDK resources (file system, executors, AssertJ failure
        // reporting), as a consumer would use the library. Unit tests stay in src/test.
        register<JvmTestSuite>("integrationTest") {
            useJUnitJupiter(libs.versions.junit)
            dependencies {
                implementation(project())
            }
            targets.configureEach {
                testTask.configure {
                    shouldRunAfter(tasks.named("test"))
                }
            }
        }
    }
}

tasks.named("check") {
    dependsOn(testing.suites.named("integrationTest"))
}

// KDoc examples are @sample references to these executable tests, so every documented example compiles and runs.
extensions.configure<org.jetbrains.dokka.gradle.DokkaExtension> {
    dokkaSourceSets.configureEach {
        samples.from("src/test/kotlin/me/ahoo/test/asserts/samples")
    }
}
