plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api("org.slf4j:slf4j-api:${Versions.slf4j}")
}
