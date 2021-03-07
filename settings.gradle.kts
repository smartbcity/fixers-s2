pluginManagement {
	repositories {
		gradlePluginPortal()
		jcenter()
	}
}

rootProject.name = "s2"

enableFeaturePreview("GRADLE_METADATA")

include(
	"s2-dsl:s2-dsl-automate"
)

include(
	"s2-automate:s2-automate-core"
)
include(
	"s2-spring:automate:s2-spring-boot-starter-automate",
	"s2-spring:automate:s2-spring-boot-starter-automate-ssm",
	"s2-spring:automate:s2-spring-boot-starter-automate-data"
)

include(
	"s2-spring:utils:s2-spring-boot-starter-utils-logger"
)

include(
	"sample:did-sample",
	"sample:did-sample:did-app",
	"sample:did-sample:did-http:did-http-app",
	"sample:did-sample:did-rsocket:did-rsocket-app",
	"sample:did-sample:did-domain",
	"sample:did-sample:did-ui"
)
