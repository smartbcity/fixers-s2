plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
}

dependencies {
	api(project(":s2-automate:s2-automate-dsl"))
	Dependencies.Spring.dataCommons(::api)
}
