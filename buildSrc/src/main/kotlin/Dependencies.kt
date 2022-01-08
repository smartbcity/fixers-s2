import city.smartb.gradle.dependencies.FixersPluginVersions

object PluginVersions {
	const val fixers = "0.3.1"
	const val d2 = fixers
	const val kotlin = FixersPluginVersions.kotlin
	const val springBoot = FixersPluginVersions.springBoot
	const val npmPublish = FixersPluginVersions.npmPublish
}

object Versions {
	const val springFramework = "5.3.14"
	const val jacksonKotlin = "2.13.0"
	const val javaxPersistence = "2.2"

	const val testcontainers = "1.15.1"

	const val ssm = PluginVersions.fixers
	const val f2 = PluginVersions.fixers
}
