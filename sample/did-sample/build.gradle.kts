plugins {
//    id("io.spring.dependency-management") version PluginVersions.springPom apply false
	id("org.springframework.boot") version PluginVersions.springBoot apply false

}

subprojects {
	plugins.withType(JavaPlugin::class.java).whenPluginAdded {
		the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
			imports {
				mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES) {
					bomProperty("kotlin.version", PluginVersions.kotlin)
				}
			}
		}
	}
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
