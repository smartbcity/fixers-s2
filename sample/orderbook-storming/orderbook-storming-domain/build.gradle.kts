plugins {
	id("city.smartb.fixers.gradle.kotlin.mpp")
	id("com.google.devtools.ksp") version PluginVersions.ksp
	kotlin("plugin.serialization")
}

dependencies {
	Dependencies.arrow (::commonMainApi, ::ksp)

	commonMainApi(project(":s2-automate:s2-automate-storming-dsl"))

}


//kotlin {
//	sourceSets.commonMain {
//		kotlin.srcDir("build/generated/ksp/commonMain/kotlin")
//	}
//	sourceSets.commonTest {
//		kotlin.srcDir("build/generated/ksp/commonTest/kotlin")
//	}
//	sourceSets.jsMain {
//		kotlin.srcDir("build/generated/ksp/jsMain/kotlin")
//	}
//	sourceSets.jsTest {
//		kotlin.srcDir("build/generated/ksp/jsTest/kotlin")
//	}
//	sourceSets.jvmMain {
//		kotlin.srcDir("build/generated/ksp/jvmMain/kotlin")
//	}
//	sourceSets.jvmTest {
//		kotlin.srcDir("build/generated/ksp/jvmTest/kotlin")
//	}
//}