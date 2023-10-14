plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.5")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation(project(mapOf("path" to ":objects")))
    implementation(project(mapOf("path" to ":generator")))
    implementation(project(mapOf("path" to ":serialize")))
    implementation(project(mapOf("path" to ":deserialize")))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}