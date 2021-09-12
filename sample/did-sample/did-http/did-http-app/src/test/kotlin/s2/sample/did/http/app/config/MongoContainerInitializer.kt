package s2.sample.did.http.app.config

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class MongoContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

	override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
		val container = MongodbContainer.getInstance()
		if (!container.isRunning) {
			MongodbContainer.getInstance().start()
		}
		TestPropertyValues.of(
			"spring.data.mongodb.uri=" + container.getUrl(),
		).applyTo(configurableApplicationContext.environment)
	}
}
