plugins {
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
    kotlin("jvm")
}

dependencies {


    api(project(":s2-spring:automate:s2-spring-boot-starter-automate-data"))

    implementation("city.smartb.f2:f2-spring-data-mongodb:${Versions.f2}")
    implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
}