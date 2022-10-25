plugins {
	id("io.spring.dependency-management")
	kotlin("plugin.spring")
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot")
}

springBoot {
	buildInfo()
}

dependencies {
	implementation(project(":sample:did-sample:did-app"))

	implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")

	// TODO Fix dependencies conflict with spring cloud function
	api("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${Versions.coroutines}")

	Dependencies.springTest(::testImplementation)
}
