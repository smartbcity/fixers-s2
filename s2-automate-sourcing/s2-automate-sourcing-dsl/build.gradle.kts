import city.smartb.gradle.dependencies.FixersVersions

plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")

//	id("dev.petuska.npm.publish")
}

dependencies {
    jsApi("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${FixersVersions.Kotlin.coroutines}")
	commonMainApi(project(":s2-automate-storing:s2-automate-storing-dsl"))
}
