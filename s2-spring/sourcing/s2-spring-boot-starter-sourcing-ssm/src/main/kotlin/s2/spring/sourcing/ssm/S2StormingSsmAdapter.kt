package s2.spring.sourcing.ssm

import f2.dsl.fnc.invoke
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import s2.dsl.automate.Evt
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.dsl.automate.ssm.toSsm
import s2.sourcing.dsl.event.EventRepository
import s2.spring.automate.sourcing.S2AutomateDeciderSpring
import s2.spring.automate.sourcing.S2AutomateDeciderSpringAdapter
import ssm.chaincode.dsl.model.Agent
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.data.dsl.features.query.DataSsmSessionGetQueryFunction
import ssm.data.dsl.features.query.DataSsmSessionLogListQueryFunction
import ssm.tx.dsl.features.ssm.SsmInitCommand
import ssm.tx.dsl.features.ssm.SsmTxInitFunction
import ssm.tx.dsl.features.ssm.SsmTxSessionPerformActionFunction
import ssm.tx.dsl.features.ssm.SsmTxSessionStartFunction
import kotlin.reflect.KClass

abstract class S2StormingSsmAdapter<ENTITY, STATE, EVENT, ID, EXECUTER>(executor: EXECUTER)
	: S2AutomateDeciderSpringAdapter<ENTITY, STATE, EVENT, ID, EXECUTER>(executor) where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
EVENT: WithS2Id<ID>,
EVENT: Evt,
EXECUTER : S2AutomateDeciderSpring<ENTITY, STATE, EVENT, ID> {

	@Autowired
	lateinit var ssmTxInitFunction: SsmTxInitFunction

	@Autowired
	lateinit var ssmSessionStartFunction: SsmTxSessionStartFunction

	@Autowired
	lateinit var ssmSessionPerformActionFunction: SsmTxSessionPerformActionFunction

	@Autowired
	lateinit var dataSsmSessionGetQueryFunction: DataSsmSessionGetQueryFunction

	@Autowired
	lateinit var dataSsmSessionLogFunction: DataSsmSessionLogListQueryFunction


	@OptIn(InternalSerializationApi::class)
	@Bean
	open fun eventStore(): EventRepository<EVENT, ID> = runBlocking {
		val automate = automate()
		EventPersisterSsm(automate, entityType()).also { ee ->
			ee.ssmSessionStartFunction = ssmSessionStartFunction
			ee. dataSsmSessionLogFunction = dataSsmSessionLogFunction
			ee.ssmSessionPerformActionFunction = ssmSessionPerformActionFunction
			ee.dataSsmSessionGetQueryFunction = dataSsmSessionGetQueryFunction
			ee.chaincodeUri = chaincodeUri()
			ee.agentSigner = signerAgent()
			ee.json = json()
			ssmTxInitFunction.invoke(
				SsmInitCommand(
					signerName = signerAgent().name,
					ssm = automate.toSsm(),
					agent = ee.agentSigner,
					chaincodeUri = chaincodeUri()
				)
			)
		}
	}

	open fun json(): Json = Json{}

	abstract fun entityType(): KClass<EVENT>
	abstract fun chaincodeUri(): ChaincodeUri
	abstract fun signerAgent(): Agent
}
