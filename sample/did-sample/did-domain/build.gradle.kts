plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("lt.petuska.npm.publish")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":s2-dsl:s2-dsl-aggregate"))

                api("city.smartb.f2:f2-dsl-function:${Versions.f2}")
                api("city.smartb.f2:f2-client-ktor:${Versions.f2}")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kserialization}")
            }
        }
        jsMain {
            dependencies {
            }
        }
        jvmMain {
            dependencies {

            }
        }
    }
}
