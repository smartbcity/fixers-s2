plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")

	kotlin("plugin.serialization")
//	id("dev.petuska.npm.publish")
}

kotlin {
	sourceSets {
		commonMain {
			dependencies {
				implementation(project(":s2-automate:s2-automate-dsl"))
				api("city.smartb.f2:f2-client-ktor:${Versions.f2}")
			}
		}

	}
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}
