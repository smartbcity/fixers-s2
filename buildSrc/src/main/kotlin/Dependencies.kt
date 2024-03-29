import city.smartb.gradle.dependencies.FixersDependencies
import city.smartb.gradle.dependencies.FixersPluginVersions
import city.smartb.gradle.dependencies.FixersVersions
import city.smartb.gradle.dependencies.Scope
import city.smartb.gradle.dependencies.add

object PluginVersions {
	val fixers = FixersPluginVersions.fixers
	val d2 = FixersPluginVersions.fixers
	const val ksp = FixersPluginVersions.ksp
	const val kotlin = FixersPluginVersions.kotlin
	const val springBoot = FixersPluginVersions.springBoot
	const val npmPublish = FixersPluginVersions.npmPublish
}

object Versions {
	const val arrow = "1.0.2-alpha.42"

	const val springBoot = FixersVersions.Spring.boot
	const val springframework = FixersVersions.Spring.framework
	const val testcontainers = "1.18.3"
//	const val testcontainers = FixersVersions.Test.testcontainers

	val ssm = FixersPluginVersions.fixers
	val f2 = FixersPluginVersions.fixers
	val i2 = FixersPluginVersions.fixers
	val coroutines = FixersVersions.Kotlin.coroutines
	val slf4j = FixersVersions.Logging.slf4j
}

object Dependencies {
	fun kserializationJson(scope: Scope) = FixersDependencies.Jvm.Json.kSerialization(scope)

	object Fixers {

		fun f2Http(scope: Scope) = scope.add(
			"city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}",
		)
		fun i2(scope: Scope) = scope.add(
			"city.smartb.i2:i2-spring-boot-starter-auth:${Versions.i2}",
		)
	}


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

		fun redis(scope: Scope) = scope.add(
			"org.springframework.boot:spring-boot-starter-data-redis-reactive:${Versions.springBoot}",
			"com.redis:spring-lettucemod:3.2.0",
			"io.lettuce:lettuce-core:6.2.2.RELEASE"
		)

		fun mongo(scope: Scope) = scope.add(
			"org.springframework.boot:spring-boot-starter-data-mongodb-reactive:${Versions.springBoot}"
		)

		fun tx(scope: Scope) = scope.add(
			"org.springframework:spring-tx:${Versions.springframework}"
		)
	}

	fun testcontainers(scope: Scope) = scope.add(
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
