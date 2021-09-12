package s2.spring.automate.ssm.persister

import com.fasterxml.jackson.databind.ObjectMapper
import f2.dsl.fnc.invoke
import s2.automate.core.context.InitTransitionAppliedContext
import s2.automate.core.context.TransitionAppliedContext
import s2.automate.core.persist.AutomatePersister
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.ssm.config.S2SsmProperties
import ssm.chaincode.dsl.SsmChaincodeProperties
import ssm.chaincode.dsl.SsmContext
import ssm.chaincode.dsl.SsmSession
import ssm.chaincode.dsl.query.SsmGetSessionQuery
import ssm.chaincode.dsl.query.SsmGetSessionQueryFunction
import ssm.chaincode.f2.SsmSessionPerformActionCommand
import ssm.chaincode.f2.SsmSessionPerformActionFunction
import ssm.chaincode.f2.SsmSessionStartCommand
import ssm.chaincode.f2.SsmSessionStartFunction
import ssm.sdk.sign.model.Signer
import ssm.sdk.sign.model.SignerAdmin

class SsmAutomatePersister<STATE, ID, ENTITY>(
	private val ssmSessionStartFunction: SsmSessionStartFunction,
	private val ssmSessionPerformActionFunction: SsmSessionPerformActionFunction,
	private val ssmGetSessionFunction: SsmGetSessionQueryFunction,
	private val signer: Signer,
	private val signerAdmin: SignerAdmin,
	private val objectMapper: ObjectMapper,
	private val config: S2SsmProperties,
) : AutomatePersister<STATE, ID, ENTITY> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {

	lateinit var entityType: Class<ENTITY>

	override suspend fun persist(
		transitionContext: TransitionAppliedContext<STATE, ID, ENTITY>,
	): ENTITY {
		val entity = transitionContext.entity
		val sessionName = entity.s2Id().toString()
		val iteration = getIteration(sessionName)

		val context = SsmSessionPerformActionCommand(
			chaincode = SsmChaincodeProperties(
				baseUrl = config.ssm.baseUrl,
				channelId = null,
				chaincodeId = null,
			),
			action = transitionContext.command::class.simpleName!!,
			signer = signer,
			context = SsmContext(
				session = entity.s2Id().toString(),
				public = objectMapper.writeValueAsString(entity),
				private = mapOf(),
				iteration = iteration,
			),
			bearerToken = null,
		)
		ssmSessionPerformActionFunction.invoke(context)
		return entity
	}

	private suspend fun getIteration(sessionId: String): Int {
		val query = SsmGetSessionQuery(
			chaincode = SsmChaincodeProperties(
				baseUrl = config.ssm.baseUrl,
				channelId = null,
				chaincodeId = null,
			),
			bearerToken = null,
			name = sessionId
		)
		val session = ssmGetSessionFunction.invoke(query).session ?: return 0
		return session.iteration
	}

	override suspend fun load(id: ID): ENTITY? {
		val query = SsmGetSessionQuery(
			chaincode = SsmChaincodeProperties(
				baseUrl = config.ssm.baseUrl,
				channelId = null,
				chaincodeId = null,
			),
			bearerToken = null,
			name = id.toString()
		)
		val session = ssmGetSessionFunction.invoke(query).session ?: return null
		return objectMapper.readValue(session.public as String, entityType)
	}

	override suspend fun persist(transitionContext: InitTransitionAppliedContext<STATE, ID, ENTITY>): ENTITY {
		val entity = transitionContext.entity
		val automate = transitionContext.automateContext.automate
		val ssmStart = SsmSessionStartCommand(
			chaincode = SsmChaincodeProperties(
				baseUrl = config.ssm.baseUrl,
				channelId = null,
				chaincodeId = null,
			),
			signerAdmin = signerAdmin,
			session = SsmSession(
				ssm = automate.name,
				session = entity.s2Id().toString(),
				roles = mapOf(signer.name to automate.transitions.get(0).role::class.simpleName!!),
				public = objectMapper.writeValueAsString(entity),
				private = mapOf()
			),
			bearerToken = null,
		)
		ssmSessionStartFunction.invoke(ssmStart)
		return entity
	}
}
