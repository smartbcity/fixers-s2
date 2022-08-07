plugins {
	kotlin("plugin.spring") version PluginVersions.kotlin apply false
	kotlin("plugin.serialization") version PluginVersions.kotlin apply false

	kotlin("kapt") version PluginVersions.kotlin apply false

	id("dev.petuska.npm.publish") version PluginVersions.npmPublish apply false
	id("com.moowork.node" ) version "1.2.0"

	id("city.smartb.fixers.gradle.config") version PluginVersions.fixers
	id("city.smartb.fixers.gradle.sonar") version PluginVersions.fixers
	id("city.smartb.fixers.gradle.d2") version PluginVersions.d2

	id("city.smartb.fixers.gradle.kotlin.mpp") version PluginVersions.fixers apply false
	id("city.smartb.fixers.gradle.kotlin.jvm") version PluginVersions.fixers apply false
	id("city.smartb.fixers.gradle.publish") version PluginVersions.fixers apply false
}

allprojects {
	group = "city.smartb.s2"
	version = System.getenv("VERSION") ?: "experimental-SNAPSHOT"
	repositories {
		mavenCentral()
		maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
		maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
		mavenLocal()
	}
}


subprojects {
	plugins.withType(city.smartb.fixers.gradle.config.ConfigPlugin::class.java).whenPluginAdded {
		fixers {
			bundle {
				id = "s2"
				name = "S2"
				description = "Wrapper around SSM"
				url = "https://gitlab.smartb.city/fixers/s2"
			}
		}
	}
	plugins.withType(dev.petuska.npm.publish.NpmPublishPlugin::class.java).whenPluginAdded {
		the<dev.petuska.npm.publish.extension.NpmPublishExtension>().apply {
			organization.set("smartb")
			registries {
				register("npmjs") {
					uri.set(uri("https://registry.npmjs.org"))
					authToken.set(java.lang.System.getenv("NPM_TOKEN"))
				}
			}
		}
	}
}

tasks {
	create<com.moowork.gradle.node.yarn.YarnTask>("installYarn") {
		dependsOn("build")
		args = listOf("install")
	}

	create<com.moowork.gradle.node.yarn.YarnTask>("storybook") {
		dependsOn("yarn_install")
		args = listOf("storybook")
	}
}

fixers {
	d2 {
		outputDirectory = file("storybook/d2/")
	}
}
