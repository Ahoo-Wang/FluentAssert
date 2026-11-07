dependencies {
    api("org.assertj:assertj-core")
}

// Without it, the module path derives the name from the file name: `fluent.assert.core`, which is invalid because
// `assert` is a Java keyword. The name follows the library's only package and is a published contract.
tasks.named<Jar>("jar") {
    manifest {
        attributes["Automatic-Module-Name"] = "me.ahoo.test.asserts"
    }
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

// ModulePathIntegrationTest puts the jar consumers get, with its runtime dependencies, on a JPMS module path.
tasks.named<Test>("integrationTest") {
    val libraryJar = tasks.named<Jar>("jar").flatMap { it.archiveFile }
    val libraryRuntime = files(configurations.named("runtimeClasspath"))
    inputs.file(libraryJar).withPropertyName("libraryJar").withNormalizer(ClasspathNormalizer::class)
    inputs.files(libraryRuntime).withPropertyName("libraryRuntime").withNormalizer(ClasspathNormalizer::class)
    jvmArgumentProviders.add(
        CommandLineArgumentProvider {
            listOf(
                "-Dfluentassert.libraryJar=${libraryJar.get().asFile.absolutePath}",
                "-Dfluentassert.libraryRuntime=${libraryRuntime.asPath}",
            )
        }
    )
}

// KDoc examples are @sample references to these executable tests, so every documented example compiles and runs.
extensions.configure<org.jetbrains.dokka.gradle.DokkaExtension> {
    dokkaSourceSets.configureEach {
        samples.from("src/test/kotlin/me/ahoo/test/asserts/samples")
    }
}

// DocumentationTest reads the READMEs, skill docs and ABI dump; declare them as inputs so doc-only changes rerun it.
tasks.named<Test>("test") {
    systemProperty("fluentassert.rootDir", rootDir.absolutePath)
    systemProperty("fluentassert.version", project.version.toString())
    inputs.files(
        rootProject.files("README.md", "README.zh-CN.md"),
        rootProject.fileTree("skills/fluent-assert") { include("**/*.md") },
        file("api/core.api"),
    ).withPathSensitivity(PathSensitivity.RELATIVE)
    inputs.property("fluentassert.version", project.version.toString())
}
