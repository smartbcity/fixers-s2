pluginManagement {
	repositories {
		gradlePluginPortal()
		maven { url = uri("https://oss.sonatype.org/service/local/repositories/releases/content") }
	}
}

rootProject.name = "s2"

include(
	"s2-automate:s2-automate-core",
	"s2-automate:s2-automate-dsl",
	"s2-automate:s2-automate-storming",
	"s2-automate:s2-automate-storming-dsl"
)
include(
	"s2-spring:automate:s2-spring-boot-starter-automate",
	"s2-spring:automate:s2-spring-boot-starter-automate-data",
	"s2-spring:automate:s2-spring-boot-starter-automate-ssm"
)

include(
	"s2-spring:storming:s2-spring-boot-starter-storming",
	"s2-spring:storming:s2-spring-boot-starter-storming-data",
	"s2-spring:storming:s2-spring-boot-starter-storming-ssm",
)

include(
	"s2-spring:utils:s2-spring-boot-starter-utils-logger"
)

include(
	"sample:multiautomate",
	"sample:multiautomate:multiautomate-app"
)

include(
	"sample:orderbook-storming",
	"sample:orderbook-storming:orderbook-storming-app-mongodb",
	"sample:orderbook-storming:orderbook-storming-app-ssm",
	"sample:orderbook-storming:orderbook-storming-domain"
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
