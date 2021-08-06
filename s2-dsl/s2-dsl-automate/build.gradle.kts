plugins {
    kotlin("multiplatform")
    id("lt.petuska.npm.publish")
}

dependencies {
    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
    commonMainApi("city.smartb.ssm:ssm-chaincode-dsl:${Versions.ssm}")
}

apply(from = rootProject.file("gradle/publishing-mpp.gradle"))
