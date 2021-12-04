plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")

	id("lt.petuska.npm.publish")
}

dependencies {
    jsApi("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.5.2")
    commonMainApi("city.smartb.f2:f2-dsl-cqrs:${Versions.f2}")
    commonMainApi("city.smartb.ssm:ssm-chaincode-dsl:${Versions.ssm}")
}
