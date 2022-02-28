plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("kapt")
}

dependencies {
	api(project(":s2-spring:sourcing:s2-spring-boot-starter-sourcing"))

	Dependencies.springAutoConfigure(::implementation, ::kapt)

	api("city.smartb.ssm:ssm-data-spring-boot-starter:${Versions.ssm}")
	api("city.smartb.ssm:ssm-tx-spring-boot-starter:${Versions.ssm}")
}
