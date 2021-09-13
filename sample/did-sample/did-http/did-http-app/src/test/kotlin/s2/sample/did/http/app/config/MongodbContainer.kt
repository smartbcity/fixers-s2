package s2.sample.did.http.app.config

import org.testcontainers.containers.GenericContainer

open class MongodbContainer : GenericContainer<MongodbContainer>(IMAGE_VERSION) {

	companion object {
		private const val IMAGE_VERSION = "mongo:4.2.12-bionic"
		private var container: MongodbContainer? = null

		fun getInstance(): MongodbContainer {
			if (container == null) {
				container = MongodbContainer().apply {
					withEnv("MONGO_INITDB_ROOT_USERNAME", USERNAME)
					withEnv("MONGO_INITDB_ROOT_PASSWORD", PASSWORD)
					withEnv("MONGO_INITDB_DATABASE", DATABASE)
					withExposedPorts(MONGO_PORT)
				}
			}
			return container!!
		}
	}

	private val USERNAME = "admin"
	private val PASSWORD = "admin"
	private val DATABASE = "admin"
	private val MONGO_PORT = 27017

	override fun start() {
		if (!this.isRunning) {
			super.start()
		}

	}

	fun getUrl(): String {
		val port = this.getMappedPort(MONGO_PORT)
		return "mongodb://${USERNAME}:${PASSWORD}@localhost:${port}/${DATABASE}"
	}
}
