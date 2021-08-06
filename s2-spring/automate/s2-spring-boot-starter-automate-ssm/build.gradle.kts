plugins {
	kotlin("jvm")
	kotlin("kapt")
}

dependencies {
	api(project(":s2-automate:s2-automate-core"))
	api(project(":s2-spring:automate:s2-spring-boot-starter-automate"))

	api("city.smartb.ssm:f2-query:${Versions.ssm}")
	api("city.smartb.ssm:ssm-sdk-sign:${Versions.ssm}")
	api("city.smartb.ssm:f2-session-start:${Versions.ssm}")
	api("city.smartb.ssm:f2-session-perform-action:${Versions.ssm}")

}

apply(from = rootProject.file("gradle/publishing.gradle"))