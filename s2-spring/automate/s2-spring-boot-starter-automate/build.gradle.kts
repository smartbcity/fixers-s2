plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("kapt")
}

dependencies {
	api(project(":s2-automate:s2-automate-core"))

	api("city.smartb.f2:f2-spring-boot-starter-function:${Versions.f2}")

	implementation("org.springframework.boot:spring-boot-autoconfigure:${Versions.springBoot}")
	kapt("org.springframework.boot:spring-boot-configuration-processor:${Versions.springBoot}")

	api("javax.persistence:javax.persistence-api:${Versions.javaxPersistence}")
	api("org.springframework:spring-context:${Versions.springFramework}")
	api("org.springframework.data:spring-data-commons:${Versions.springData}")

}
