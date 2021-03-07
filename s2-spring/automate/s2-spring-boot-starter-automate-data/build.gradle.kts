plugins {
	kotlin("jvm")
	kotlin("kapt")
}

dependencies {
	api(project(":s2-automate:s2-automate-core"))
	api(project(":s2-spring:automate:s2-spring-boot-starter-automate"))

	implementation("city.smartb.ssm:ssm-f2:${Versions.f2}")


	implementation("javax.persistence:javax.persistence-api:${Versions.javaxPersistence}")
	implementation("org.springframework:spring-context:${Versions.springFramework}")
	implementation("org.springframework.data:spring-data-commons:${Versions.springData}")

}

apply(from = rootProject.file("gradle/publishing.gradle"))