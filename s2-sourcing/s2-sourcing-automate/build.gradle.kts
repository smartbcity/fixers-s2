plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")
//	id("dev.petuska.npm.publish")
}

dependencies {
	commonMainApi(project(":s2-automate:s2-automate-core"))
	commonMainApi(project(":s2-sourcing:s2-sourcing-dsl"))
}
