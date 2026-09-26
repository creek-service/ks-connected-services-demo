pluginManagement {
    repositories {
        mavenLocal()
        maven {
            // Required to resolve Creek's `0.4.5-SNAPSHOT` Gradle plugins while it's not yet
            // released. Remove once Creek `0.5.0` is released.
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        }
        gradlePluginPortal()
    }
}

rootProject.name = "ks-connected-services-demo"

include(
    "handle-occurrence-filtering-service",
    "api",
    "handle-occurrence-service",
    "services",
    "system-tests"
)
