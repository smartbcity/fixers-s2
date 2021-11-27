import city.smartb.gradle.dependencies.FixersVersions

plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")

	id("lt.petuska.npm.publish")
}

dependencies {
    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${FixersVersions.f2}")
    commonMainApi("city.smartb.ssm:ssm-chaincode-dsl:${Versions.ssm}")
}
