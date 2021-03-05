plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    api(project(":s2-automate:s2-automate-core"))
    implementation("org.springframework.boot:spring-boot-autoconfigure:${Versions.springBoot}")
    kapt("org.springframework.boot:spring-boot-configuration-processor:${Versions.springBoot}")

    implementation("javax.persistence:javax.persistence-api:${Versions.javaxPersistence}")
    implementation("org.springframework:spring-context:${Versions.springFramework}")
    implementation("org.springframework.data:spring-data-commons:${Versions.springData}")



}

apply(from = rootProject.file("gradle/publishing.gradle"))