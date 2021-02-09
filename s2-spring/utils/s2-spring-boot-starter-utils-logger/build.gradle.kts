plugins {
    id("io.spring.dependency-management")
    kotlin("jvm")
}

dependencies {
    implementation("org.slf4j:slf4j-api")
}

apply(from = rootProject.file("gradle/publishing.gradle"))