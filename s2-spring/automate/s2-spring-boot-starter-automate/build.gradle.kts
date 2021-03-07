plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    api(project(":s2-automate:s2-automate-core"))
    implementation("org.springframework.boot:spring-boot-autoconfigure:${Versions.springBoot}")
    kapt("org.springframework.boot:spring-boot-configuration-processor:${Versions.springBoot}")

    api("javax.persistence:javax.persistence-api:${Versions.javaxPersistence}")
    api("org.springframework:spring-context:${Versions.springFramework}")
    api("org.springframework.data:spring-data-commons:${Versions.springData}")

}

apply(from = rootProject.file("gradle/publishing.gradle"))