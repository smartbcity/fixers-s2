import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("multiplatform") version PluginVersions.kotlin apply false
	kotlin("jvm") version PluginVersions.kotlin apply false
	kotlin("plugin.spring") version PluginVersions.kotlin apply false
	kotlin("plugin.serialization") version PluginVersions.kotlin apply false
	kotlin("kapt") version PluginVersions.kotlin apply false
	id("org.jetbrains.dokka") version PluginVersions.dokka

	id("org.sonarqube") version PluginVersions.sonarQube

	id("lt.petuska.npm.publish") version PluginVersions.npmPublish apply false
	id("com.moowork.node" ) version "1.2.0"

}

allprojects {
	group = "city.smartb.s2"
	version = System.getenv("VERSION") ?: "latest"
	repositories {
		jcenter()
		mavenCentral()
		maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
		maven("https://repo.spring.io/snapshot")
		maven("https://repo.spring.io/milestone")
	}
}



subprojects {
	plugins.withType(org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper::class.java).whenPluginAdded {
		the<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension>().apply {
			jvm {
				compilations.all {
					kotlinOptions.jvmTarget = "11"
				}
			}
			js(IR) {
				binaries.library()
				browser {
					browser()
					binaries.executable()

					testTask {
						useKarma {
							useChromeHeadless()
						}
					}
				}
			}
			sourceSets {
				val commonMain by getting {
					dependencies {
						Dependencies.common.coroutines.forEach { api(it) }
						Dependencies.common.kserialization.forEach { api(it) }
					}
				}
				val commonTest by getting {
					dependencies {
						implementation(kotlin("test-common"))
						implementation(kotlin("test-annotations-common"))
					}
				}
				val jvmMain by getting
				val jvmTest by getting {
					dependencies {
						implementation(kotlin("reflect"))
						Dependencies.jvm.coroutines.forEach { implementation(it) }
					}
				}
				val jsMain by getting {
					dependencies {
					}
				}
				val jsTest by getting {
					dependencies {
						implementation(kotlin("test-js"))
					}
				}
			}
		}
	}
	plugins.withType(lt.petuska.npm.publish.NpmPublishPlugin::class.java).whenPluginAdded {
		the<lt.petuska.npm.publish.dsl.NpmPublishExtension>().apply {
			organization = "smartb"
			repositories {
				repository("npmjs") {
					registry = uri("https://registry.npmjs.org")
					authToken = System.getenv("NPM_TOKEN")
				}
			}
			publications {
				publication("js") {
					packageJson {
						bundledDependencies("kotlin") { // Always includes "kotlin" dependency and filters out the rest by the spec
							-"ktor-ktor.*".toRegex() // Exclude "kotlin-test" dependency
						}
					}
				}
			}

		}
	}
	plugins.withType(JavaPlugin::class.java).whenPluginAdded {
		tasks.withType<KotlinCompile>().configureEach {
			println("Configuring $name in project ${project.name}...")
			kotlinOptions {
				freeCompilerArgs = listOf("-Xjsr305=strict")
				jvmTarget = "11"
			}
		}
		tasks.withType<JavaCompile> {
			sourceCompatibility = JavaVersion.VERSION_11.toString()
			targetCompatibility = JavaVersion.VERSION_11.toString()
		}

		tasks.withType<Test> {
			useJUnitPlatform()
		}

		dependencies {
			val implementation by configurations

			implementation(kotlin("reflect"))
			Dependencies.jvm.coroutines.forEach{implementation(it)}
		}
	}

	plugins.apply("org.jetbrains.dokka")
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