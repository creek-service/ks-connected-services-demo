plugins {
    `java-library`
}

dependencies {
    api("org.creekservice:creek-kafka-metadata:${property("creekVersion")}")

    compileOnly("com.github.spotbugs:spotbugs-annotations:${property("spotBugsVersion")}")

    // To avoid dependency hell downstream, avoid adding any more dependencies except Creek metadata jars and test dependencies.

    testImplementation("org.apache.kafka:kafka-clients:${property("kafkaVersion")}")
}
