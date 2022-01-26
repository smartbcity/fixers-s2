plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot") version PluginVersions.springBoot
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

dependencies {
	api(project(":sample:orderbook-storming:orderbook-storming-domain"))

	api(project(":s2-spring:storming:s2-spring-boot-starter-storming-data"))
	implementation("city.smartb.f2:f2-spring-data-mongodb:${Versions.f2}")

	Dependencies.f2Http (::implementation)
	Dependencies.kserializationJson (::implementation)

	Dependencies.testcontainersMongo(::testImplementation)
	Dependencies.springTest(::testImplementation)
}
