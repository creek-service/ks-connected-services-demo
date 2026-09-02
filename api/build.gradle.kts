plugins {
    `java-library`
}

val kafkaVersion: String by extra
val creekVersion : String by extra
val spotBugsVersion : String by extra

dependencies {
    api("org.creekservice:creek-kafka-metadata:$creekVersion")

    compileOnly("com.github.spotbugs:spotbugs-annotations:$spotBugsVersion")

    // To avoid dependency hell downstream, avoid adding any more dependencies except Creek metadata jars and test dependencies.

    testImplementation("org.apache.kafka:kafka-clients:$kafkaVersion")
}
