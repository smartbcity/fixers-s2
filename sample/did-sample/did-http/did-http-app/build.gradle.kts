import city.smartb.gradle.dependencies.FixersVersions
import city.smartb.gradle.dependencies.FixersDependencies

plugins {
	id("io.spring.dependency-management")
	kotlin("plugin.spring")
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot")
}

springBoot {
	buildInfo()
}

dependencies {
	implementation(project(":sample:did-sample:did-app"))

	implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${FixersVersions.f2}")
	api("com.google.code.gson:gson:2.8.8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jacksonKotlin}")

	testImplementation("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
}
