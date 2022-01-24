plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot") version PluginVersions.springBoot
	kotlin("plugin.spring")
	id("com.google.devtools.ksp") version "1.6.10-1.0.2"
}

dependencies {
	Dependencies.arrow (::implementation, ::ksp)

	api(project(":s2-automate:s2-automate-storming"))
//	api(project(":s2-spring:storming:s2-spring-boot-starter-storming"))
	api(project(":s2-spring:storming:s2-spring-boot-starter-storming-data"))

	implementation("city.smartb.f2:f2-spring-data-mongodb:${Versions.f2}")
	implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")

	Dependencies.testcontainersMongo(::testImplementation)
	Dependencies.springTest(::testImplementation)
}

kotlin {
	sourceSets.main {
		kotlin.srcDir("build/generated/ksp/main/kotlin")
	}
	sourceSets.test {
		kotlin.srcDir("build/generated/ksp/test/kotlin")
	}
}