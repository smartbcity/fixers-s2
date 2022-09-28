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

	Dependencies.dataMongo(::implementation)

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testImplementation("org.testcontainers:junit-jupiter:${Versions.testcontainers}")
}
