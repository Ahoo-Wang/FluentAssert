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
    configure<KotlinJvmProjectExtension> {
        jvmToolchain(17)
        // The public API is the overload set itself; lock it with a reviewed ABI dump (core/api/).
        @OptIn(ExperimentalAbiValidation::class)
        abiValidation()
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
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
