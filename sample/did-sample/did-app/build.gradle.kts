import city.smartb.gradle.dependencies.FixersVersions

plugins {
	id("io.spring.dependency-management")
	kotlin("plugin.spring")
	id("city.smartb.fixers.gradle.kotlin.jvm")
}

dependencies {
	api(project(":sample:did-sample:did-domain"))

	api(project(":s2-spring:automate:s2-spring-boot-starter-automate-ssm"))

	api("city.smartb.f2:f2-spring-data-mongodb:${FixersVersions.f2}")
	api("city.smartb.f2:f2-spring-boot-starter-function:${FixersVersions.f2}")

	api("city.smartb.f2:f2-feature-version:${FixersVersions.f2}")
	api("city.smartb.f2:f2-feature-catalog:${FixersVersions.f2}")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
