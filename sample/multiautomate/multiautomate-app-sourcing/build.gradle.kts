plugins {
	id("io.spring.dependency-management")
	id("city.smartb.fixers.gradle.kotlin.jvm")

	kotlin("plugin.spring")
}

dependencies {
	api(project(":s2-spring:sourcing:s2-spring-boot-starter-sourcing-data"))

	Dependencies.Fixers.f2Http(::implementation)
	Dependencies.Spring.mongo(::api)
}
