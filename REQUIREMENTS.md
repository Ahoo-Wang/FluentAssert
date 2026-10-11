# Requirements

FluentAssert exposes AssertJ assertions as Kotlin `.assert()` extension functions. This document states
what the library must guarantee and where each requirement is verified. A change that alters a
requirement must update this file, its verification, and [CHANGELOG.md](CHANGELOG.md).

## Functional requirements

| ID | Requirement | Verified by |
|----|-------------|-------------|
| F1 | Every supported receiver type resolves, by its **static** type, to the most specific AssertJ assertion class listed in the README "Supported types" table. | `OverloadResolutionContractTest` (compile-time typed assignments); `DocumentationTest` (table covers the ABI) |
| F2 | Every receiver except `AssertProvider<A>` accepts `null` and yields an assertion on `null`. | `OverloadResolutionContractTest` (all receivers are `null`), `Jdk*Test` nullable cases |
| F3 | No JDK type covered by the library makes `.assert()` ambiguous (e.g. `ScheduledFuture`, `StringBuilder`, `CharBuffer`). | `OverloadResolutionContractTest`, `JdkConcurrentTest` |
| F4 | `assertThrownBy<T> { }` returns `ThrowableAssert<T>` for a thrown `T`, fails for no throwable or a wrong type, and does not leak its diagnostics into chained assertions. | `ThrowableTest` |
| F5 | `AssertProvider<A>.assert()` returns the provider's own assertion `A`. | `AssertProviderTest`, `FailureReportingIntegrationTest` |
| F6 | Assertions behave exactly like AssertJ, including failure messages; the library adds no assertion semantics. | `FailureReportingIntegrationTest`, `FileSystemIntegrationTest`, `ConcurrencyIntegrationTest` |

## Non-functional requirements

| ID | Requirement | Verified by |
|----|-------------|-------------|
| N1 | **Binary compatibility**: public signatures are only added within a major version. | `checkKotlinAbi` against `core/api/core.api` |
| N2 | **Consumer compatibility**: usable from Kotlin 2.1+ compilers and JDK 17+; the published `kotlin-stdlib` dependency is 2.2.x. | Kotlin `languageVersion`/`apiVersion` 2.2 and `jvmToolchain(17)` in `build.gradle.kts`; a consumer build with Kotlin 2.1.21 compiles and passes, 2.0.21 is rejected (verified manually for 1.2.0) |
| N3 | **Zero overhead**: each extension is a single delegation to an AssertJ constructor or factory. | Code review; 100% line coverage gate |
| N4 | **Minimal dependencies**: the published POM depends only on `assertj-core` and `kotlin-stdlib`. | Generated POM review on release |
| N5 | **Quality gates**: every change passes unit and integration tests, detekt, 100% line coverage and license-header checks in CI. | `./gradlew build` in `.github/workflows/ci.yml` |
| N6 | **Correct documentation**: every documented code example compiles and passes, and the type tables and install versions match the code. | KDoc `@sample` tests in `samples/`; `DocumentationTest` |
