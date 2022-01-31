plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("com.google.devtools.ksp") version PluginVersions.ksp
	kotlin("plugin.serialization")
}

dependencies {
	Dependencies.arrow (::commonMainApi, ::ksp)

	commonMainApi(project(":s2-sourcing:s2-sourcing-automate"))

}
