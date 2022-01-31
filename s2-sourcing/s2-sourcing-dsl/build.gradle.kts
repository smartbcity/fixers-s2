import city.smartb.gradle.dependencies.FixersVersions

plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")

	id("lt.petuska.npm.publish")
}

dependencies {
    jsApi("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${FixersVersions.Kotlin.coroutines}")
	commonMainApi(project(":s2-automate:s2-automate-dsl"))
}
