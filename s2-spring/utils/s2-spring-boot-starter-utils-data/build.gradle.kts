plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	Dependencies.Spring.dataCommons(::api)
}
