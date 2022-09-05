plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("kapt")
}

dependencies {
	api(project(":s2-automate-storing:s2-automate-storing-executor"))

	Dependencies.Spring.autoConfigure(::implementation, ::kapt)
}
