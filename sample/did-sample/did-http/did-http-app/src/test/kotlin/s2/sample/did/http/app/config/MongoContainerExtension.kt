package s2.sample.did.http.app.config

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class MongoContainerExtension: BeforeAllCallback, AfterAllCallback {

    override fun beforeAll(context: ExtensionContext?) {
        MongodbContainer.getInstance().start()
    }

    override fun afterAll(context: ExtensionContext?) {
        MongodbContainer.getInstance().stop()
    }
}