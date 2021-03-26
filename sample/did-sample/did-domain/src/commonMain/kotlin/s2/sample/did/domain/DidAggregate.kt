package s2.sample.did.domain

import s2.sample.did.domain.features.*

interface DidAggregate {
	suspend fun createDid(cmd: DidCreateCommand): DidCreatedEvent
	suspend fun addPublicKey(cmd: DidAddPublicKeyCommand): DidAddPublicKeyEvent
	suspend fun revokePublicKey(cmd: DidRevokePublicKeyCommand): DidRevokedPublicKeyEvent
	suspend fun revoke(cmd: DidRevokeCommand): DidRevokedEvent
}