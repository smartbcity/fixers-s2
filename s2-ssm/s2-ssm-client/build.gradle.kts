plugins {
    kotlin("multiplatform")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":s2-dsl:s2-dsl-aggregate"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
            }
        }
        jsMain {
            dependencies {
            }
        }
        jvmMain {
            dependencies {
                api ("city.smartb.ssm:ssm-sdk-client:${Versions.ssm}")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.coroutines}")
            }
        }
    }
}
