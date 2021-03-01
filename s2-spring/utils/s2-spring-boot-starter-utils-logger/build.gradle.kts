plugins {
    kotlin("jvm")
}

dependencies {
    implementation("org.slf4j:slf4j-api:${Versions.slf4j}")
}

apply(from = rootProject.file("gradle/publishing.gradle"))