import city.smartb.gradle.dependencies.FixersVersions

plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("kapt")
}

dependencies {
	api(project(":s2-automate:s2-automate-core"))
	api(project(":s2-automate:s2-automate-storming"))
	api(project(":s2-spring:storming:s2-spring-boot-starter-storming"))

//	api("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")

	Dependencies.springAutoConfigure(::implementation, ::kapt)
	api("javax.persistence:javax.persistence-api:${Versions.javaxPersistence}")
	api("org.springframework.data:spring-data-commons:${Versions.springDataCommons}")

}
