pluginManagement {
	repositories {
		gradlePluginPortal()
		jcenter()
	}
}

rootProject.name = "s2"

enableFeaturePreview("GRADLE_METADATA")

include(
	"s2-dsl:s2-dsl-automate",
	"s2-dsl:s2-dsl-aggregate"
)

include(
	"s2-spring:aggregate:s2-spring-boot-starter-aggregate",
	"s2-spring:aggregate:s2-spring-boot-starter-aggregate-storming"
)

include(
	"s2-spring:utils:s2-spring-boot-starter-utils-logger"
)

include(
	"sample:did-sample",
	"sample:did-sample:did-app",
	"sample:did-sample:did-app-storming",
	"sample:did-sample:did-http:did-http-app",
	"sample:did-sample:did-rsocket:did-rsocket-app",
	"sample:did-sample:did-domain",
	"sample:did-sample:did-ui"
)
