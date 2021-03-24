package s2.spring.automate.ssm.persister

import com.fasterxml.jackson.databind.ObjectMapper
import f2.function.spring.invokeSingle
import s2.automate.core.context.InitTransitionContext
import s2.automate.core.context.TransitionContext
import s2.automate.core.persist.AutotmatePersister
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.ssm.config.S2SsmProperties
import ssm.client.domain.Signer
import ssm.client.domain.SignerAdmin
import ssm.dsl.SsmContext
import ssm.dsl.SsmSession
import ssm.dsl.query.SsmGetSessionFunction
import ssm.dsl.query.SsmGetSessionQuery
import ssm.f2.SsmSessionPerformActionCommand
import ssm.f2.SsmSessionPerformActionFunction
import ssm.f2.SsmSessionStartCommand
import ssm.f2.SsmSessionStartFunction

class SsmAutomatePersister<STATE, ID, ENTITY>(
	private val ssmSessionStartFunction: SsmSessionStartFunction,
	private val ssmSessionPerformActionFunction: SsmSessionPerformActionFunction,
	private val ssmGetSessionFunction: SsmGetSessionFunction,
	private val signer: Signer,
	private val signerAdmin: SignerAdmin,
	private val objectMapper: ObjectMapper,
	private val config: S2SsmProperties
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

		val context = SsmSessionPerformActionCommand(
			action = transitionContext.command::class.simpleName!!,
			signer = signer,
			baseUrl = config.ssm.baseUrl,
			context = SsmContext(
			session= entity.s2Id().toString(),
			public = objectMapper.writeValueAsString(entity),
			private = mapOf(),
			iteration = iteration,
		))
		val event = ssmSessionPerformActionFunction.invokeSingle(context)
		return entity
	}

	private suspend fun getIteration(sessionId: String): Int {
		val query = SsmGetSessionQuery(
			baseUrl = config.ssm.baseUrl,
			jwt = null,
			sessionId
		)
		val session = ssmGetSessionFunction.invokeSingle(query).session ?: return 0
		return session.iteration
	}

	override suspend fun load(id: ID): ENTITY? {
		val query = SsmGetSessionQuery(
			baseUrl = config.ssm.baseUrl,
			jwt = null,
			id.toString()
		)
		val session = ssmGetSessionFunction.invokeSingle(query).session ?: return null
		return objectMapper.readValue(session.public, entityType)
	}

	override suspend fun persist(transitionContext: InitTransitionContext<STATE, ID, ENTITY>, entity: ENTITY): ENTITY {
		val automate = transitionContext.automateContext.automate
		val ssmStart = SsmSessionStartCommand(
			signerAdmin = signerAdmin,
			baseUrl = config.ssm.baseUrl,
			session = SsmSession(
				ssm= automate.name,
				session = entity.s2Id().toString(),
				roles = mapOf(signer.name to automate.transitions.get(0).role::class.simpleName!!),
				public = objectMapper.writeValueAsString(entity),
				private = mapOf()
		))
		val event = ssmSessionStartFunction.invokeSingle(ssmStart)
		return entity
	}

}
