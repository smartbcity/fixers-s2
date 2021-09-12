pluginManagement {
	repositories {
		gradlePluginPortal()
		maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
	}
}

rootProject.name = "s2"

include(
	"s2-dsl:s2-dsl-automate"
)

include(
	"s2-automate:s2-automate-core"
)
include(
	"s2-spring:automate:s2-spring-boot-starter-automate",
//	"s2-spring:automate:s2-spring-boot-starter-automate-f2",
	"s2-spring:automate:s2-spring-boot-starter-automate-ssm",
	"s2-spring:automate:s2-spring-boot-starter-automate-data"
)

include(
	"s2-spring:utils:s2-spring-boot-starter-utils-logger"
)

include(
	"sample:multiautomate",
	"sample:multiautomate:multiautomate-app"
)

include(
	"sample:did-sample",
	"sample:did-sample:did-app",
	"sample:did-sample:did-http:did-http-app",
	"sample:did-sample:did-rsocket:did-rsocket-app",
	"sample:did-sample:did-domain",
	"sample:did-sample:did-ui"
)
