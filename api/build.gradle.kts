plugins {
    `java-library`
}

dependencies {
    api("org.creekservice:creek-kafka-metadata:${property("creekVersion")}")

    compileOnly("com.github.spotbugs:spotbugs-annotations:${property("spotBugsVersion")}")

    testImplementation("org.apache.kafka:kafka-clients:${property("kafkaVersion")}")

}
