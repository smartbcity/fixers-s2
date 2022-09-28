plugins {
	id("io.spring.dependency-management")
	kotlin("plugin.spring")
	id("city.smartb.fixers.gradle.kotlin.jvm")
}

dependencies {
	api(project(":sample:did-sample:did-domain"))
	api(project(":s2-spring:storing:s2-spring-boot-starter-storing-ssm"))
	Dependencies.dataMongo(::implementation)

//	api("city.smartb.f2:f2-feature-version:${Versions.f2}")
//	api("city.smartb.f2:f2-feature-catalog:${Versions.f2}")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
