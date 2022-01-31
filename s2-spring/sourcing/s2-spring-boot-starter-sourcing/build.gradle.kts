plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("kapt")
}

dependencies {
	api(project(":s2-sourcing:s2-sourcing-automate"))
	//TODO REMOVE dependency to :s2-spring:automate:s2-spring-boot-starter-automate
	api(project(":s2-spring:automate:s2-spring-boot-starter-automate"))

	Dependencies.springAutoConfigure(::implementation, ::kapt)
}
