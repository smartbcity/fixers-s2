plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot") version PluginVersions.springBoot
	kotlin("plugin.spring")
	id("com.google.devtools.ksp") version PluginVersions.ksp
	kotlin("plugin.serialization")
}

dependencies {
	implementation(project(":sample:orderbook-sourcing:orderbook-sourcing-domain"))
	implementation(project(":s2-spring:sourcing:s2-spring-boot-starter-sourcing-ssm"))

	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive:${Versions.springBoot}")

//	implementation("org.springframework.data:spring-data-redis-reactive:${Versions.springData}")
	implementation("io.lettuce:lettuce-core:6.1.6.RELEASE")

	Dependencies.f2Http (::implementation)
	Dependencies.kserializationJson (::implementation)
	Dependencies.arrow (::implementation, ::ksp)

	Dependencies.springTest(::testImplementation)
}
