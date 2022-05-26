plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot") version PluginVersions.springBoot
	kotlin("plugin.spring")
	kotlin("plugin.serialization")
}

dependencies {
	api(project(":sample:orderbook-sourcing:orderbook-sourcing-domain"))

	api(project(":s2-spring:sourcing:s2-spring-boot-starter-sourcing-data"))
	implementation("city.smartb.f2:f2-spring-data-mongodb:${Versions.f2}")

	Dependencies.f2Http (::implementation)
	Dependencies.kserializationJson (::implementation)
	Dependencies.springRedis(::api)

	Dependencies.testcontainersMongo(::testImplementation)
	Dependencies.springTest(::testImplementation)
}
