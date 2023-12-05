plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(mapOf("path" to ":crud")))
    implementation(project(mapOf("path" to ":objects")))

    implementation("javax.servlet:javax.servlet-api:4.0.1")
    compileOnly("javax.servlet:jstl:1.2")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:9.0.50")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:9.0.50")
}

tasks.test {
    useJUnitPlatform()
}