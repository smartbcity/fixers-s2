package s2.sample.did.domain

import s2.sample.did.domain.features.*

interface DidAggregate {
	fun createDid(): DidCreateCommandFunction
	fun addPublicKey(): DidAddPublicKeyCommandFunction
	fun revokePublicKey(): DidRevokeCommandFunction
	fun revoke(): DidRevokePublicKeyCommandFunction
}