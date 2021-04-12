package s2.sample.did.app

import f2.function.spring.adapter.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import s2.sample.did.app.config.DidS2Aggregate
import s2.sample.did.app.entity.DidEntity
import s2.sample.did.domain.DidAggregate
import s2.sample.did.domain.DidState
import s2.sample.did.domain.features.*

@Service
class DidAggregateBean(
//	private val didS2Config: DidS2Config,
	private val didS2Aggregate: DidS2Aggregate
): DidAggregate {

	@Bean("createDid")
	fun createDidFnc() = f2Function<DidCreateCommand, DidCreatedEvent> { cmd ->
		createDid(cmd)
	}

	@Bean("addPublicKey")
	fun addPublicKeyFnc() = f2Function<DidAddPublicKeyCommand, DidAddPublicKeyEvent> { cmd ->
		addPublicKey(cmd)
	}
	@Bean("revokePublicKey")
	fun revokePublicKey() = f2Function<DidRevokePublicKeyCommand, DidRevokedPublicKeyEvent> { cmd ->
		revokePublicKey(cmd)
	}

	@Bean("revoke")
	fun revokeFnc() = f2Function<DidRevokeCommand, DidRevokedEvent> { cmd ->
		revoke(cmd)
	}

	override suspend fun createDid(cmd: DidCreateCommand): DidCreatedEvent = didS2Aggregate.createWithEvent(cmd,
		{ DidCreatedEvent(s2Id(), DidState(this.state)) }
	) {
		DidEntity(id = cmd.id, state = DidState.Created().position)
	}

	override suspend fun addPublicKey(cmd: DidAddPublicKeyCommand): DidAddPublicKeyEvent = didS2Aggregate.doTransition(cmd) {
		this.publicKeys.add(cmd.publicKey)
		this.state = DidState.Actived().position
		this to DidAddPublicKeyEvent(
			id = cmd.id,
			type = DidState(this.state)
		)
	}

	override suspend fun revokePublicKey(cmd: DidRevokePublicKeyCommand): DidRevokedPublicKeyEvent {
		TODO("Not yet implemented")
	}

	override suspend fun revoke(cmd: DidRevokeCommand): DidRevokedEvent {
		TODO("Not yet implemented")
	}

}