
plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.serialization")
//	id("dev.petuska.npm.publish")
}

dependencies {
    commonMainApi("city.smartb.ssm:ssm-chaincode-dsl:${Versions.ssm}")
}
