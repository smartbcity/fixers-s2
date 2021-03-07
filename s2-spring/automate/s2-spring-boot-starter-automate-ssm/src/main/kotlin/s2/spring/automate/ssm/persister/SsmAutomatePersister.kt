package s2.spring.automate.ssm.persister

import com.fasterxml.jackson.databind.ObjectMapper
import f2.function.spring.invokeSingle
import org.springframework.stereotype.Service
import s2.automate.core.context.InitTransitionContext
import s2.automate.core.context.TransitionContext
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.automate.core.persist.AutotmatePersister
import s2.dsl.automate.S2State
import ssm.dsl.SsmContext
import ssm.dsl.SsmSession
import ssm.dsl.command.SsmPerformCommand
import ssm.dsl.command.SsmPerformFunction
import ssm.dsl.command.SsmStartCommand
import ssm.dsl.command.SsmStartFunction
import ssm.dsl.query.GetSsmSessionFunction
import ssm.dsl.query.GetSsmSessionQuery
import ssm.client.domain.Signer

@Service
class SsmAutomatePersister<STATE, ID, ENTITY>(
	private val start: SsmStartFunction,
	private val perform: SsmPerformFunction,
	private val getSsmSession: GetSsmSessionFunction,
	private val signer: Signer,
	private val objectMapper: ObjectMapper,
) : AutotmatePersister<STATE, ID, ENTITY> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {

	lateinit var entityType: Class<ENTITY>

	override suspend fun persist(
		transitionContext: TransitionContext<STATE, ID, ENTITY>,
		entity: ENTITY,
	): ENTITY {
		val sessionName = entity.s2Id().toString()
		transitionContext.to
		val iteration = getIteration(sessionName)
		val context = SsmPerformCommand(
			transitionContext.command::class.simpleName!!,
			SsmContext(
			session= entity.s2Id().toString(),
			public = objectMapper.writeValueAsString(entity),
			private = mapOf(),
			iteration = iteration,
		))
		val event = perform.invokeSingle(context)
		return entity
	}

	private suspend fun getIteration(sessionId: String): Int {
		val session = getSsmSession.invokeSingle(GetSsmSessionQuery(sessionId)).session ?: return 0
		return session.iteration
	}


	override suspend fun load(id: ID): ENTITY? {
		val session = getSsmSession.invokeSingle(GetSsmSessionQuery(id.toString())).session ?: return null
		return objectMapper.readValue(session.public, entityType)
	}

	override suspend fun persist(transitionContext: InitTransitionContext<STATE, ID, ENTITY>, entity: ENTITY): ENTITY {
		val automate = transitionContext.automateContext.automate
		val ssmStart = SsmStartCommand(
			session = SsmSession(
				ssm= automate.name,
				session = entity.s2Id().toString(),
				roles = mapOf(signer.name to automate.transitions.get(0).role::class.simpleName!!),
				public = objectMapper.writeValueAsString(entity),
				private = mapOf()
		))
		val event = start.invokeSingle(ssmStart)
		return entity
	}


}

