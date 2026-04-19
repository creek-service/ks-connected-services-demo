[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![codecov](https://codecov.io/gh/creek-service/ks-connected-services-demo/branch/main/graph/badge.svg)](https://codecov.io/gh/creek-service/ks-connected-services-demo)
[![build](https://github.com/creek-service/ks-connected-services-demo/actions/workflows/build.yml/badge.svg)](https://github.com/creek-service/ks-connected-services-demo/actions/workflows/build.yml)
[![CodeQL](https://github.com/creek-service/ks-connected-services-demo/actions/workflows/codeql.yml/badge.svg)](https://github.com/creek-service/ks-connected-services-demo/actions/workflows/codeql.yml)

# Kafka Streams Connected Services Tutorial

Repo containing the completed [Connected Services tutorial](https://www.creekservice.org/ks-connected-services-demo)
and associated [docs](docs/README.md).

### Gradle commands

* `./gradlew` should be the go-to local command to run when developing.
              It will run `./gradlew format`, `./gradlew static` and `./gradlew check`.
* `./gradlew format` will format the code using [Spotless][spotless].
* `./gradlew static` will run static code analysis, i.e. [Spotbugs][spotbugs] and [Checkstyle][checkstyle].
* `./gradlew check` will run all checks and tests.
* `./gradlew jacocoTestReport` will generate [Jacoco][jacoco] coverage reports for each module.

[spotless]: https://github.com/diffplug/spotless
[spotbugs]: https://spotbugs.github.io/
[checkstyle]: https://checkstyle.sourceforge.io/
[jacoco]: https://www.jacoco.org/jacoco/trunk/doc/
