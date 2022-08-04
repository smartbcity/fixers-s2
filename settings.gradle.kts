pluginManagement {
	repositories {
		gradlePluginPortal()
		maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
		maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
		mavenLocal()
	}
}

rootProject.name = "s2"

include(
	"s2-automate:s2-automate-core",
	"s2-automate:s2-automate-dsl",
	"s2-automate:s2-automate-storing"
)

include(
	"s2-sourcing:s2-sourcing-dsl",
	"s2-sourcing:s2-sourcing-automate"
)

include(
	"s2-spring:automate:s2-spring-boot-starter-automate",
	"s2-spring:automate:s2-spring-boot-starter-automate-data",
	"s2-spring:automate:s2-spring-boot-starter-automate-ssm"
)

include(
	"s2-spring:sourcing:s2-spring-boot-starter-sourcing",
	"s2-spring:sourcing:s2-spring-boot-starter-sourcing-data",
	"s2-spring:sourcing:s2-spring-boot-starter-sourcing-ssm",
)

include(
	"s2-spring:utils:s2-spring-boot-starter-utils-logger"
)

include(
	"sample:multiautomate",
	"sample:multiautomate:multiautomate-app-sourcing",
	"sample:multiautomate:multiautomate-app-storing"
)

include(
	"sample:orderbook-sourcing",
	"sample:orderbook-sourcing:orderbook-sourcing-app-mongodb",
	"sample:orderbook-sourcing:orderbook-sourcing-app-ssm",
	"sample:orderbook-sourcing:orderbook-sourcing-domain"
)

include(
	"sample:subautomate:subautomate-app"
)

//include(
//	"sample:did-sample",
//	"sample:did-sample:did-app",
//	"sample:did-sample:did-http:did-http-app",
//	"sample:did-sample:did-rsocket:did-rsocket-app",
//	"sample:did-sample:did-domain",
//	"sample:did-sample:did-ui"
//)
