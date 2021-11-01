package s2.sample.did.domain.client

import f2.client.F2Client
import f2.client.declaration
import f2.client.ktor.Protocol
import f2.dsl.fnc.F2Supplier
import s2.sample.did.domain.DidAggregate
import s2.sample.did.domain.features.DidAddPublicKeyCommandFunction
import s2.sample.did.domain.features.DidCreateCommandFunction
import s2.sample.did.domain.features.DidRevokeCommandFunction
import s2.sample.did.domain.features.DidRevokePublicKeyCommandFunction

//expect fun didClient(protocol: Protocol, host: String, port: Int, path: String? = null): F2Supplier<DIDFunctionClient>

open class DIDFunctionClient constructor(private val client: F2Client) : DidAggregate {

	override fun createDid(): DidCreateCommandFunction = client.declaration("createDid")

	override fun addPublicKey(): DidAddPublicKeyCommandFunction = client.declaration("addPublicKey")

	override fun revokePublicKey(): DidRevokeCommandFunction = client.declaration("revokePublicKey")

	override fun revoke(): DidRevokePublicKeyCommandFunction = client.declaration("revoke")
}
