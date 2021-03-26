package s2.sample.did.domain.client

import f2.client.ktor.F2ClientBuilder
import s2.sample.did.domain.DidAggregate
import f2.client.ktor.Protocol
import f2.client.ktor.get
import f2.client.F2Client
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import s2.sample.did.domain.features.*

open class DidAggregateClient(
	private val client: F2Client,
) : DidAggregate {


	companion object {
		suspend fun  get(protocol: Protocol, host: String, port: Int, path: String? = null): DidAggregateClient {
			val client = F2ClientBuilder.get(protocol, host, port, path)
			return DidAggregateClient(client)
		}
	}

	override suspend fun createDid(cmd: DidCreateCommand): DidCreatedEvent {
		val body = Json.encodeToString(cmd)
		val ret = client.invoke(this::createDid.name, body)
		try {
			return Json.decodeFromString(ret)
		} catch (e: Exception) {
			return Json.decodeFromString<List<DidCreatedEvent>>(ret).first()
		}
	}

	override suspend fun addPublicKey(cmd: DidAddPublicKeyCommand): DidAddPublicKeyEvent {
		val body = Json.encodeToString(cmd)
		val ret = client.invoke(this::addPublicKey.name, body)
		try {
			return Json.decodeFromString(ret)
		} catch (e: Exception) {
			return Json.decodeFromString<List<DidAddPublicKeyEvent>>(ret).first()
		}
	}

	override suspend fun revokePublicKey(cmd: DidRevokePublicKeyCommand): DidRevokedPublicKeyEvent {
		val body = Json.encodeToString(cmd)
		val ret = client.invoke(this::revokePublicKey.name, body)
		try {
			return Json.decodeFromString(ret)
		} catch (e: Exception) {
			return Json.decodeFromString<List<DidRevokedPublicKeyEvent>>(ret).first()
		}
	}

	override suspend fun revoke(cmd: DidRevokeCommand): DidRevokedEvent {
		val body = Json.encodeToString(cmd)
		val ret = client.invoke(this::revoke.name, body)
		try {
			return Json.decodeFromString(ret)
		} catch (e: Exception) {
			return Json.decodeFromString<List<DidRevokedEvent>>(ret).first()
		}
	}

}
