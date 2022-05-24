import city.smartb.gradle.dependencies.FixersPluginVersions
import city.smartb.gradle.dependencies.FixersDependencies
import city.smartb.gradle.dependencies.FixersVersions
import city.smartb.gradle.dependencies.Scope
import city.smartb.gradle.dependencies.add

object PluginVersions {
	val fixers = FixersPluginVersions.fixers
	const val d2 = "0.8.2"
	const val ksp = FixersPluginVersions.ksp
	const val kotlin = FixersPluginVersions.kotlin
	const val springBoot = FixersPluginVersions.springBoot
	const val npmPublish = FixersPluginVersions.npmPublish
}

object Versions {
	const val arrow = "1.0.2-alpha.42"

	const val springBoot = FixersVersions.Spring.boot
	const val testcontainers = FixersVersions.Test.testcontainers

	val ssm = PluginVersions.fixers
	val f2 = PluginVersions.fixers
}

object Dependencies {
	fun kserializationJson(scope: Scope) = FixersDependencies.Jvm.Json.kSerialization(scope)

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

	object Spring {
		fun dataCommons(scope: Scope) = FixersDependencies.Jvm.Spring.dataCommons(scope)
		fun autoConfigure(scope: Scope, ksp: Scope) = FixersDependencies.Jvm.Spring.autoConfigure(scope, ksp)
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