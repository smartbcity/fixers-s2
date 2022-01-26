plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot") version PluginVersions.springBoot
	kotlin("plugin.spring")
	id("com.google.devtools.ksp") version PluginVersions.ksp
	kotlin("plugin.serialization")
}

dependencies {
	implementation(project(":sample:orderbook-storming:orderbook-storming-domain"))
	implementation(project(":s2-spring:storming:s2-spring-boot-starter-storming-ssm"))

	Dependencies.f2Http (::implementation)
	Dependencies.kserializationJson (::implementation)
	Dependencies.arrow (::implementation, ::ksp)

	Dependencies.springTest(::testImplementation)
}
