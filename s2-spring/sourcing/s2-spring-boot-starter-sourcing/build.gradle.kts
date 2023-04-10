plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("kapt")
}

dependencies {
	api(project(":s2-spring:storing:s2-spring-boot-starter-storing"))

	Dependencies.Spring.tx(::implementation)
	Dependencies.Spring.autoConfigure(::implementation, ::kapt)
}
