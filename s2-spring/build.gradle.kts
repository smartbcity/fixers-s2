    plugins {
    id("io.spring.dependency-management") version PluginVersions.springPom apply false
    id("org.springframework.boot") version PluginVersions.springBoot apply false

    kotlin("jvm")
}

subprojects {
    plugins.withType(JavaPlugin::class.java).whenPluginAdded {
        the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
            imports {
                mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES) {
                    bomProperty("kotlin.version", PluginVersions.kotlin)
                }
                mavenBom("org.springframework.cloud:spring-cloud-dependencies:${PluginVersions.springCloudPom}")
            }
        }
    }
}