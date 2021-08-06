object PluginVersions {
	const val springBoot = "2.5.3"
	const val kotlin = "1.5.21"
	const val dokka = "1.4.20"
	const val npmPublish = "1.0.4"
	const val sonarQube = "3.0"
}

object Versions {
	const val springBoot = PluginVersions.springBoot
	const val springFramework = "5.3.4"
	const val springData = "2.4.5"

	const val jacksonKotlin  = "2.12.1"
	const val javaxPersistence = "2.2"
	const val slf4j = "1.7.26"

	const val coroutines = "1.5.1"
	const val kserialization = "1.1.0"
	const val ktor = "1.6.1"
	const val rsocket = "0.12.0"
	const val testcontainers = "1.15.1"

	const val f2 = "experimental-SNAPSHOT"
	const val ssm = "experimental-SNAPSHOT"
}

object Dependencies {
	object jvm {
		val coroutines = arrayOf(
			"org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}",
			"org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${Versions.coroutines}",
			"org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${Versions.coroutines}",
			"org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.coroutines}"
		)
	}

	object common {
		val coroutines = arrayOf(
			"org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
		)
		val kserialization = arrayOf(
			"org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kserialization}",
			"org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kserialization}"
		)
	}
}