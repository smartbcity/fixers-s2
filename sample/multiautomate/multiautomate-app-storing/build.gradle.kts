plugins {
	id("io.spring.dependency-management")
	id("city.smartb.fixers.gradle.kotlin.jvm")

	kotlin("plugin.spring")
}

dependencies {
	api(project(":s2-spring:storing:s2-spring-boot-starter-storing-data"))

	Dependencies.f2Http(::implementation)
	Dependencies.dataMongo(::api)
}
