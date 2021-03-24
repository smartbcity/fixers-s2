plugins {
	kotlin("jvm")
	kotlin("kapt")
}

dependencies {
	api(project(":s2-automate:s2-automate-core"))
	api(project(":s2-spring:automate:s2-spring-boot-starter-automate"))

	api("city.smartb.ssm:ssm-sdk-client:${Versions.f2}")
	api("city.smartb.ssm:ssm-f2-query:${Versions.f2}")
	api("city.smartb.ssm:ssm-f2-session-start:${Versions.f2}")
	api("city.smartb.ssm:ssm-f2-session-perform-action:${Versions.f2}")

}

apply(from = rootProject.file("gradle/publishing.gradle"))