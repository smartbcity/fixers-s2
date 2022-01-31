package s2.sample.orderbook.sourcing.app.mongodb.config

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import s2.sample.orderbook.sourcing.app.mongodb.SubAutomateMongodbApp

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [SubAutomateMongodbApp::class])
abstract class SpringTestBase {

	companion object {
		@JvmStatic
		@Container
		val mongoContainer: MongoDBContainer =
			MongoDBContainer(DockerImageName.parse("mongo:4.4")).apply {
				start()
			}

		@JvmStatic
		@DynamicPropertySource
		fun setProperties(registry: DynamicPropertyRegistry) {
			registry.add("spring.data.mongodb.uri", mongoContainer::getReplicaSetUrl)
		}

	}

}