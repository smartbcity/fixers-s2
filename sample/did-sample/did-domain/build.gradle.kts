plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")

	kotlin("plugin.serialization")
//	id("dev.petuska.npm.publish")
}

dependencies {
	commonMainImplementation(project(":s2-automate:s2-automate-dsl"))
	commonMainApi("city.smartb.f2:f2-client-ktor:${Versions.f2}")

	jvmTestImplementation(project(":s2-automate:s2-automate-documenter"))
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}
