import f2.client.ktor.F2ClientBuilder
import f2.client.ktor.Protocol
import f2.client.ktor.get
import s2.sample.did.domain.client.DidAggregateClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlinx.serialization.InternalSerializationApi
import s2.sample.did.domain.features.*
import kotlin.js.Promise

@JsExport
fun  didClient(protocol: Protocol, host: String, port: Int, path: String? = null):  Promise<DidAggregateJsClient> = GlobalScope.promise {
	val client = F2ClientBuilder.get(protocol, host, port, path)
	val f2Client = DidAggregateClient(client)
	DidAggregateJsClient(f2Client)
}

@JsExport
open class DidAggregateJsClient(
	private val client: DidAggregateClient,
) {

	@InternalSerializationApi
	fun createDid(cmd: DidCreateCommand): Promise<DidCreatedEvent> = GlobalScope.promise {
		client.createDid(cmd)
	}

	@InternalSerializationApi
	fun addPublicKey(cmd: DidAddPublicKeyCommand): Promise<DidAddPublicKeyEvent> = GlobalScope.promise {
		client.addPublicKey(cmd)
	}

	@InternalSerializationApi
	fun revokePublicKey(cmd: DidRevokePublicKeyCommand): Promise<DidRevokedPublicKeyEvent> = GlobalScope.promise {
		client.revokePublicKey(cmd)
	}

	@InternalSerializationApi
	fun revoke(cmd: DidRevokeCommand): Promise<DidRevokedEvent> = GlobalScope.promise {
		client.revoke(cmd)
	}

}
