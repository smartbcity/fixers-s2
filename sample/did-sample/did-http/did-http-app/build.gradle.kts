plugins {
	id("io.spring.dependency-management")
	kotlin("plugin.spring")
	id("city.smartb.fixers.gradle.kotlin.jvm")
	id("org.springframework.boot")
}

springBoot {
	buildInfo()
}

dependencies {

	implementation(project(":sample:did-sample:did-app"))

	api("city.smartb.ssm:f2-query:${Versions.ssm}")
	api("city.smartb.ssm:f2-create-ssm:${Versions.ssm}")

	implementation("city.smartb.f2:f2-spring-boot-starter-function-http:${Versions.f2}")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jacksonKotlin}")

	testImplementation("org.testcontainers:junit-jupiter:${Versions.testcontainers}")

	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
}
