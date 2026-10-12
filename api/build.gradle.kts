plugins {
    `java-library`
    id("org.creekservice.schema.json")
}

dependencies {
    api("org.creekservice:creek-kafka-metadata:${property("creekVersion")}")

    compileOnlyApi("com.github.spotbugs:spotbugs-annotations:${property("spotBugsVersion")}")
    compileOnlyApi("com.fasterxml.jackson.core:jackson-annotations:${property("jacksonAnnotationsVersion")}")
    compileOnlyApi("org.creekservice:creek-base-annotation:${property("creekVersion")}")
    compileOnlyApi("io.swagger.core.v3:swagger-annotations:${property("swaggerAnnotationsVersion")}")

    testImplementation("org.apache.kafka:kafka-clients:${property("kafkaVersion")}")

}
