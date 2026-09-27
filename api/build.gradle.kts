plugins {
    `java-library`
    id("org.creekservice.schema.json")
}

val kafkaVersion: String by extra
val creekVersion : String by extra
val spotBugsVersion : String by extra
val jacksonAnnotationsVersion : String by extra

dependencies {
    api("org.creekservice:creek-kafka-metadata:$creekVersion")
    implementation("org.creekservice:creek-base-annotation:$creekVersion")
    // Used to control the fidelity of generated JSON schemas, e.g. marking non-primitive
    // record components as required:
    api("com.fasterxml.jackson.core:jackson-annotations:$jacksonAnnotationsVersion")

    compileOnly("com.github.spotbugs:spotbugs-annotations:$spotBugsVersion")

    // To avoid dependency hell downstream, avoid adding any more dependencies except Creek metadata jars and test dependencies.

    testImplementation("org.apache.kafka:kafka-clients:$kafkaVersion")

    jsonSchemaGenerator("org.creekservice:creek-json-schema-generator:$creekVersion")
}

creek.schema.json {
    typeScanning.moduleWhiteList(moduleName)
    subTypeScanning.moduleWhiteList(moduleName)
}
