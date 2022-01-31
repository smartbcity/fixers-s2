import city.smartb.gradle.dependencies.FixersPluginVersions
import city.smartb.gradle.dependencies.FixersDependencies
import city.smartb.gradle.dependencies.FixersVersions
import city.smartb.gradle.dependencies.Scope
import city.smartb.gradle.dependencies.add

object PluginVersions {
	const val fixers = "0.3.1"
	const val d2 = fixers
	const val ksp = "1.6.10-1.0.2"
	const val kotlin = FixersPluginVersions.kotlin
	const val springBoot = FixersPluginVersions.springBoot
	const val npmPublish = FixersPluginVersions.npmPublish
}

object Versions {
	const val arrow = "1.0.2-alpha.42"
	const val serialization = "1.3.2"

	const val springBoot = FixersVersions.Spring.boot
	const val springFramework = "5.3.14"
	const val springDataCommons = "2.6.0"
	const val jacksonKotlin = "2.13.0"
	const val javaxPersistence = "2.2"

	const val testcontainers = "1.16.3"

	const val ssm = "experimental-SNAPSHOT"
	const val f2 = "experimental-SNAPSHOT"
}

object Dependencies {

	fun kserializationJson(scope: Scope) = scope.add(
		"org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}",
	)

	fun f2Http(scope: Scope) = scope.add(
		"city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}",
	)

	fun arrow(scope: Scope, ksp: Scope) = scope.add(
		"io.arrow-kt:arrow-core:${Versions.arrow}",
		"io.arrow-kt:arrow-optics:${Versions.arrow}",
	).also {
		ksp.add(
			"io.arrow-kt:arrow-optics-ksp-plugin:${Versions.arrow}"
		)
	}

	fun springDataCommon(scope: Scope) = scope.add(
		"javax.persistence:javax.persistence-api:${Versions.javaxPersistence}",
		"org.springframework.data:spring-data-commons:${Versions.springDataCommons}"
	)

	fun springAutoConfigure(scope: Scope, ksp: Scope) = scope.add(
		"org.springframework.boot:spring-boot-autoconfigure:${FixersVersions.Spring.boot}"
	).also {
		ksp.add(
			"org.springframework.boot:spring-boot-configuration-processor:${FixersVersions.Spring.boot}"
		)
	}

	fun testcontainersMongo(scope: Scope) = scope.add(
		"org.testcontainers:junit-jupiter:${Versions.testcontainers}",
		"org.testcontainers:mongodb:${Versions.testcontainers}",
	)

	fun springTest(scope: Scope) = scope.add(
		"org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}",
	).also {
		junit(scope)
	}

	fun junit(scope: Scope) = FixersDependencies.Jvm.Test.junit(scope)
	fun cucumber(scope: Scope) = FixersDependencies.Jvm.Test.cucumber(scope)
}