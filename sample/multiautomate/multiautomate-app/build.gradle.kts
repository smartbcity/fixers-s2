plugins {
	id("io.spring.dependency-management")
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")

	kotlin("plugin.spring")
}

dependencies {
	api(project(":s2-spring:automate:s2-spring-boot-starter-automate-data"))

	implementation("city.smartb.f2:f2-spring-data-mongodb:${Versions.f2}")
	implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")
}
