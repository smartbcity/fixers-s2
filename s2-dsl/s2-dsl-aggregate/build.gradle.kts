plugins {
    kotlin("multiplatform")
    id("lt.petuska.npm.publish")
}

dependencies {
    commonMainApi(project(":s2-dsl:s2-dsl-automate"))

    commonMainApi("city.smartb.f2:f2-dsl-event:${Versions.f2}")
}

apply(from = rootProject.file("gradle/publishing-mpp.gradle"))
