import city.smartb.gradle.dependencies.FixersVersions

plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")
	id("lt.petuska.npm.publish")
}

dependencies {
	commonMainApi(project(":s2-automate:s2-automate-dsl"))

	commonMainApi("city.smartb.f2:f2-dsl-event:${FixersVersions.f2}")
	commonMainApi("city.smartb.f2:f2-dsl-function:${FixersVersions.f2}")
}
