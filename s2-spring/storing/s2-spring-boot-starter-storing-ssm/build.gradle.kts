plugins {
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("city.smartb.fixers.gradle.publish")
	kotlin("plugin.spring")
}

dependencies {
	api(project(":s2-automate:s2-automate-core"))
	api(project(":s2-spring:storing:s2-spring-boot-starter-storing"))

	api("city.smartb.ssm:ssm-data-spring-boot-starter:${Versions.ssm}")
	api("city.smartb.ssm:ssm-tx-spring-boot-starter:${Versions.ssm}")
	api("city.smartb.ssm:ssm-tx-init-ssm-spring-boot-starter:${Versions.ssm}")
}
