<!-- PR title format: [category] : summary   e.g. [feature] : add Char overload -->

## Summary

<!-- What changes and why. Link the issue: Fixes #123 -->

## Type of change

- [ ] Bug fix (non-breaking)
- [ ] New overload / feature (minor: may change resolution of existing call sites when recompiled)
- [ ] Breaking change (major: removes or changes a public signature)
- [ ] Build, CI or documentation only

## Checklist

- [ ] `./gradlew build` passes (unit + integration tests, detekt, ABI check, coverage gate, license headers)
- [ ] New/changed overloads are pinned in `OverloadResolutionContractTest`
- [ ] Public API changes are reflected in `core/api/core.api` (`./gradlew :core:updateKotlinAbi`)
- [ ] README (en/zh), `skills/fluent-assert/` and `CHANGELOG.md` (Unreleased) are updated where relevant
