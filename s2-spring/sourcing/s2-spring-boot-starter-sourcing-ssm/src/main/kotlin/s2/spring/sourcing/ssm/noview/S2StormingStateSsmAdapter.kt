package s2.spring.sourcing.ssm.noview//package s2.spring.sourcing.ssm
//
//import f2.dsl.fnc.invoke
//import kotlinx.coroutines.runBlocking
//import kotlinx.serialization.InternalSerializationApi
//import kotlinx.serialization.json.Json
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.annotation.Bean
//import s2.dsl.automate.Evt
//import s2.dsl.automate.S2State
//import s2.dsl.automate.model.WithS2Id
//import s2.dsl.automate.model.WithS2State
//import s2.dsl.automate.ssm.toSsm
//import s2.sourcing.dsl.event.EventRepository
//import s2.sourcing.dsl.view.View
//import s2.sourcing.dsl.view.ViewLoader
//import s2.spring.automate.sourcing.S2AutomateDeciderSpring
//import s2.spring.automate.sourcing.S2SourcingAdapter
//import ssm.chaincode.dsl.model.Agent
//import ssm.chaincode.dsl.model.uri.ChaincodeUri
//import ssm.data.dsl.features.query.DataSsmSessionGetQueryFunction
//import ssm.data.dsl.features.query.DataSsmSessionLogListQueryFunction
//import ssm.tx.dsl.features.ssm.SsmInitCommand
//import ssm.tx.dsl.features.ssm.SsmTxInitFunction
//import ssm.tx.dsl.features.ssm.SsmTxSessionPerformActionFunction
//import ssm.tx.dsl.features.ssm.SsmTxSessionStartFunction
//import kotlin.reflect.KClass
//
//abstract class S2StormingStateSsmAdapter<STATE, EVENT, ID, EXECUTER>
//	: S2SourcingAdapter<StateEntity<STATE, ID>, STATE, EVENT, ID, EXECUTER>() where
//STATE : S2State,
//EVENT: WithS2Id<ID>,
//EVENT: WithS2State<STATE>,
//EVENT: Evt,
//EXECUTER : S2AutomateDeciderSpring<StateEntity<STATE, ID>, STATE, EVENT, ID> {
//
////	@Bean
////	open fun stormingProjectionBuilder(
////		eventStore: EventPersisterSsm<EVENT, ID>,
////		evolver: View<EVENT, StateEntity<STATE, ID>>
////	): ViewLoader<EVENT, StateEntity<STATE, ID>, ID> {
////		return ViewLoader(eventStore, evolver)
////	}
//
//	@Autowired
//	lateinit var ssmTxInitFunction: SsmTxInitFunction
//
//	@Autowired
//	lateinit var ssmSessionStartFunction: SsmTxSessionStartFunction
//
//	@Autowired
//	lateinit var ssmSessionPerformActionFunction: SsmTxSessionPerformActionFunction
//
//	@Autowired
//	lateinit var dataSsmSessionGetQueryFunction: DataSsmSessionGetQueryFunction
//
//	@Autowired
//	lateinit var dataSsmSessionLogFunction: DataSsmSessionLogListQueryFunction
//
//
//	@Bean
//	open fun viewier(): View<EVENT, StateEntity<STATE, ID>> = EntityStatusModelViewer()
//
//	@OptIn(InternalSerializationApi::class)
//	@Bean
//	open fun eventStore(): EventRepository<EVENT, ID> = runBlocking {
//		val automate = automate()
//		EventPersisterSsm(automate, entityType()).also { ee ->
//			ee.ssmSessionStartFunction = ssmSessionStartFunction
//			ee. dataSsmSessionLogFunction = dataSsmSessionLogFunction
//			ee.ssmSessionPerformActionFunction = ssmSessionPerformActionFunction
//			ee.dataSsmSessionGetQueryFunction = dataSsmSessionGetQueryFunction
//			ee.chaincodeUri = chaincodeUri()
//			ee.agentSigner = signerAgent()
//			ee.json = json()
//			ssmTxInitFunction.invoke(
//				SsmInitCommand(
//					signerName = signerAgent().name,
//					ssm = automate.toSsm(),
//					agent = ee.agentSigner
//				)
//			)
//		}
//	}
//
//
//	open fun json(): Json = Json{}
//
//	abstract fun entityType(): KClass<EVENT>
//	abstract fun chaincodeUri(): ChaincodeUri
//	abstract fun signerAgent(): Agent
//}
