plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("kapt")
}

dependencies {
	api(project(":s2-automate:s2-automate-core"))
	api(project(":s2-spring:automate:s2-spring-boot-starter-automate"))

	api("javax.persistence:javax.persistence-api:${Versions.javaxPersistence}")
	api("org.springframework:spring-context:${Versions.springFramework}")
	implementation("org.springframework.data:spring-data-commons:${Versions.springData}")
}
