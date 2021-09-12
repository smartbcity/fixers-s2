plugins {
	id("org.springframework.boot") version PluginVersions.springBoot apply false

}

tasks {
	register("cleanKts", Delete::class) {
		delete("did-ui/kotlin")
	}

	register("kts", Copy::class) {
		dependsOn("cleanKts")
		from("${this.project.buildDir.absolutePath}/js/packages/") {
			exclude("*-test")
		}

		into("did-ui/kotlin")
		includeEmptyDirs = false
	}

}
