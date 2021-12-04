import city.smartb.gradle.dependencies.FixersVersions

plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")

	kotlin("plugin.serialization")
	id("lt.petuska.npm.publish")
}

kotlin {
	sourceSets {
		commonMain {
			dependencies {
				implementation(project(":s2-automate:s2-automate-dsl"))

				api("city.smartb.f2:f2-dsl-function:${Versions.f2}")
				api("city.smartb.f2:f2-client-ktor:${Versions.f2}")
			}
		}
		jsMain {
			dependencies {
			}
		}
		jvmMain {
			dependencies {

			}
		}
	}
}
