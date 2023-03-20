plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api(project(":s2-spring:sourcing:s2-spring-boot-starter-sourcing-data"))
	Dependencies.Spring.mongo(::api)
}
