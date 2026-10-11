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
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.testretry.TestRetryPlugin
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    alias(libs.plugins.publish)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.dokka)
    alias(libs.plugins.test.retry)
    base
    alias(libs.plugins.kover)
}
val dependenciesProject = project(":dependencies")
val bomProjects = setOf(
    project(":bom"),
    dependenciesProject,
)

val codeCoverageReportProject = project(":code-coverage-report")
val publishProjects = subprojects - codeCoverageReportProject
val libraryProjects = publishProjects - bomProjects
val isInCI = !System.getenv("CI").isNullOrEmpty()

// Keep in sync with languageVersion/apiVersion (KotlinVersion.KOTLIN_2_2) below.
val KOTLIN_CONSUMER_STDLIB_VERSION = "2.2.21"
ext.set("libraryProjects", libraryProjects)

allprojects {
    repositories {
        mavenCentral()
    }
    tasks.withType<Jar> {
        manifest {
            attributes["Implementation-Title"] = project.getArchivesName()
            attributes["Implementation-Version"] = project.version
        }
    }
    apply<BasePlugin>()
    base {
        archivesName.set(project.getArchivesName())
    }
}

// Every Kotlin source file must carry the Apache-2.0 header (see LICENSE).
val checkLicenseHeaders by tasks.registering {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Verifies that all Kotlin sources start with the Apache-2.0 license header."
    val sources = fileTree(rootDir) {
        include("**/src/**/*.kt")
        exclude("**/build/**")
    }
    inputs.files(sources)
    doLast {
        val missing = sources.files.filterNot { it.readText().contains("Licensed under the Apache License, Version 2.0") }
        check(missing.isEmpty()) {
            "Missing Apache-2.0 license header:\n" + missing.joinToString("\n") { it.relativeTo(rootDir).path }
        }
    }
}
tasks.named("check") {
    dependsOn(checkLicenseHeaders)
}

configure(bomProjects) {
    apply<JavaPlatformPlugin>()
    configure<JavaPlatformExtension> {
        allowDependencies()
    }
}

configure(libraryProjects) {
    apply<DetektPlugin>()
    configure<DetektExtension> {
        config.setFrom(files("${rootProject.rootDir}/config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
        autoCorrect = false
    }
    apply<DokkaPlugin>()
    apply<JavaLibraryPlugin>()
    configure<JavaPluginExtension> {
        withJavadocJar()
        withSourcesJar()
    }
    // withJavadocJar() alone packages an empty jar for Kotlin sources; ship the Dokka HTML instead.
    tasks.named<Jar>("javadocJar") {
        from(tasks.named("dokkaGeneratePublicationHtml"))
    }
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlinx.kover")
    configure<KoverProjectExtension> {
        currentProject {
            sources {
                // Kover only recognizes the default test source set; keep test suites out of the measured code.
                excludedSourceSets.add("integrationTest")
            }
        }
        reports {
            verify {
                // Every public API is a one-line delegation, so anything below full line coverage is an untested overload.
                rule("Full line coverage") {
                    minBound(100, CoverageUnit.LINE)
                }
            }
        }
    }
    configure<KotlinJvmProjectExtension> {
        jvmToolchain(17)
        // Consumers compile against this library with their own Kotlin version: target the oldest
        // non-deprecated language/API level and publish a matching stdlib dependency instead of the
        // build compiler's, so upgrading the build toolchain never forces a consumer stdlib upgrade.
        coreLibrariesVersion = KOTLIN_CONSUMER_STDLIB_VERSION
        // The public API is the overload set itself; lock it with a reviewed ABI dump (core/api/).
        @OptIn(ExperimentalAbiValidation::class)
        abiValidation()
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            languageVersion.set(KotlinVersion.KOTLIN_2_2)
            apiVersion.set(KotlinVersion.KOTLIN_2_2)
            freeCompilerArgs = listOf("-Xjsr305=strict")
            javaParameters = true
        }
    }
    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(listOf("-parameters"))
    }
    apply<TestRetryPlugin>()
    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
        }
        retry {
            if (isInCI) {
                maxRetries = 2
                maxFailures = 20
            }
            failOnPassedAfterRetry = true
        }
    }
    dependencies {
        api(platform(dependenciesProject))
        testImplementation(platform(rootProject.libs.junit.bom))
        testImplementation("org.assertj:assertj-core")
        testImplementation(rootProject.libs.mockk) {
            exclude(group = "org.slf4j", module = "slf4j-api")
        }
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testImplementation("org.junit.jupiter:junit-jupiter-params")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
        detektPlugins(rootProject.libs.detekt.formatting)
    }
}

configure(publishProjects) {
    val isBom = bomProjects.contains(this)
    apply<MavenPublishPlugin>()
    apply<SigningPlugin>()
    configure<PublishingExtension> {
        repositories {
            maven {
                name = "projectBuildRepo"
                url = uri(layout.buildDirectory.dir("repos"))
            }
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/Ahoo-Wang/FluentAssert")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
            val linyiPackageReleaseUrl = getPropertyOf("linyiPackageReleaseUrl")
            if (linyiPackageReleaseUrl != null) {
                maven {
                    name = "LinYiPackages"
                    url = uri(linyiPackageReleaseUrl)
                    credentials {
                        username = getPropertyOf("linyiPackageUsername")
                        password = getPropertyOf("linyiPackagePwd")
                    }
                }
            }
        }
        publications {
            val publishName = if (isBom) "mavenBom" else "mavenLibrary"
            val publishComponentName = if (isBom) "javaPlatform" else "java"
            create<MavenPublication>(publishName) {
                artifactId = project.getArchivesName()
                from(components[publishComponentName])
                pom {
                    name.set(rootProject.name)
                    description.set(getPropertyOf("description"))
                    url.set(getPropertyOf("website"))
                    issueManagement {
                        system.set("GitHub")
                        url.set(getPropertyOf("issues"))
                    }
                    scm {
                        url.set(getPropertyOf("website"))
                        connection.set(getPropertyOf("vcs"))
                    }
                    licenses {
                        license {
                            name.set(getPropertyOf("license_name"))
                            url.set(getPropertyOf("license_url"))
                            distribution.set("repo")
                        }
                    }
                    developers {
                        developer {
                            id.set("ahoo-wang")
                            name.set("ahoo wang")
                            organization {
                                url.set(getPropertyOf("website"))
                            }
                        }
                    }
                }
            }
        }
    }
    configure<SigningExtension> {
        if (isInCI) {
            val signingKeyId = System.getenv("SIGNING_KEYID")
            val signingKey = System.getenv("SIGNING_SECRETKEY")
            val signingPassword = System.getenv("SIGNING_PASSWORD")
            useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
        }

        if (isBom) {
            sign(extensions.getByType(PublishingExtension::class).publications["mavenBom"])
        } else {
            sign(extensions.getByType(PublishingExtension::class).publications["mavenLibrary"])
        }
    }
}

nexusPublishing {
    this.repositories {
        sonatype {
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            username.set(System.getenv("SONATYPE_USERNAME"))
            password.set(System.getenv("SONATYPE_PASSWORD"))
        }
    }
}

fun getPropertyOf(name: String): String? = providers.gradleProperty(name).orNull
fun Project.getArchivesName() = "fluent-assert-${project.name}"
