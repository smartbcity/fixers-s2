package s2.spring.automate.ssm.persister

import com.fasterxml.jackson.databind.ObjectMapper
import f2.dsl.fnc.invoke
import f2.dsl.fnc.invokeWith
import s2.automate.core.context.AutomateContext
import s2.automate.core.context.InitTransitionAppliedContext
import s2.automate.core.context.TransitionAppliedContext
import s2.automate.core.persist.AutomatePersister
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import ssm.chaincode.dsl.model.SessionName
import ssm.chaincode.dsl.model.SsmContext
import ssm.chaincode.dsl.model.SsmSession
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.chaincode.dsl.model.uri.toSsmUri
import ssm.data.dsl.features.query.DataSsmSessionGetQuery
import ssm.data.dsl.features.query.DataSsmSessionGetQueryFunction
import ssm.sdk.sign.SignerUserProvider
import ssm.tx.dsl.features.ssm.SsmSessionPerformActionCommand
import ssm.tx.dsl.features.ssm.SsmSessionStartCommand
import ssm.tx.dsl.features.ssm.SsmTxSessionPerformActionFunction
import ssm.tx.dsl.features.ssm.SsmTxSessionStartFunction

class SsmAutomatePersister<STATE, ID, ENTITY>(
	private val ssmSessionStartFunction: SsmTxSessionStartFunction,
	private val ssmSessionPerformActionFunction: SsmTxSessionPerformActionFunction,
	private val dataSsmSessionGetQueryFunction: DataSsmSessionGetQueryFunction,
	private val signerUserProvider: SignerUserProvider,
	private val objectMapper: ObjectMapper,

) : AutomatePersister<STATE, ID, ENTITY> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {

	lateinit var chaincodeUri: ChaincodeUri
	lateinit var entityType: Class<ENTITY>

	override suspend fun persist(
		transitionContext: TransitionAppliedContext<STATE, ID, ENTITY>,
	): ENTITY {
		val entity = transitionContext.entity
		val sessionName = entity.s2Id().toString()
		val iteration = getIteration(transitionContext.automateContext, sessionName)

		val context = SsmSessionPerformActionCommand(
			action = transitionContext.command::class.simpleName!!,
			context = SsmContext(
				session = entity.s2Id().toString(),
				public = objectMapper.writeValueAsString(entity),
				private = mapOf(),
				iteration = iteration,
			),
		)
		ssmSessionPerformActionFunction.invoke(context)
		return entity
	}

	override suspend fun load(automateContext: AutomateContext<STATE, ID, ENTITY>, id: ID): ENTITY? {
		val session = getSession( id.toString(), automateContext).item ?: return null
		return objectMapper.readValue(session.state.details.public as String, entityType)
	}

	override suspend fun persist(transitionContext: InitTransitionAppliedContext<STATE, ID, ENTITY>): ENTITY {
		val entity = transitionContext.entity
		val automate = transitionContext.automateContext.automate
		val ssmStart = SsmSessionStartCommand(
			session = SsmSession(
				ssm = automate.name,
				session = entity.s2Id().toString(),
				roles = mapOf(signerUserProvider.get().name to automate.transitions.get(0).role::class.simpleName!!),
				public = objectMapper.writeValueAsString(entity),
				private = mapOf()
			),
		)
		ssmSessionStartFunction.invoke(ssmStart)
		return entity
	}

	private suspend fun getIteration(automateContext: AutomateContext<STATE, ID, ENTITY>, sessionId: SessionName): Int {
		return getSession(sessionId, automateContext)
			.item?.state?.details?.iteration ?: return 0
	}

	private suspend fun getSession(
		sessionId: SessionName,
		automateContext: AutomateContext<STATE, ID, ENTITY>
	) = DataSsmSessionGetQuery(
		sessionName = sessionId,
		ssm = chaincodeUri.toSsmUri(automateContext.automate.name)
	).invokeWith(dataSsmSessionGetQueryFunction)
}
