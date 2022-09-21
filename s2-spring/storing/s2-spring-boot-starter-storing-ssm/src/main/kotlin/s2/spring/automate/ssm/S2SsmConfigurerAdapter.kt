package s2.spring.automate.ssm

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import s2.automate.core.persist.AutomatePersister
import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.S2ConfigurerAdapter
import s2.spring.automate.executor.S2AutomateExecutorSpring
import s2.spring.automate.ssm.persister.SsmAutomatePersister
import ssm.chaincode.dsl.model.Agent
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.data.dsl.features.query.DataSsmSessionGetQueryFunction
import ssm.tx.dsl.features.ssm.SsmTxInitFunction
import ssm.tx.dsl.features.ssm.SsmTxSessionPerformActionFunction
import ssm.tx.dsl.features.ssm.SsmTxSessionStartFunction

abstract class S2SsmConfigurerAdapter<STATE, ID, ENTITY, AGGREGATE> :
	S2ConfigurerAdapter<STATE, ID, ENTITY, AGGREGATE>() where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
AGGREGATE : S2AutomateExecutorSpring<STATE, ID, ENTITY> {

	@Autowired
	lateinit var ssmTxInitFunction: SsmTxInitFunction

	@Autowired
	lateinit var ssmSessionStartFunction: SsmTxSessionStartFunction

	@Autowired
	lateinit var ssmSessionPerformActionFunction: SsmTxSessionPerformActionFunction

	@Autowired
	lateinit var dataSsmSessionGetQueryFunction: DataSsmSessionGetQueryFunction

	@Autowired
	lateinit var objectMapper: ObjectMapper

//	@Bean
	override fun aggregateRepository(): AutomatePersister<STATE, ID, ENTITY, S2Automate> {
		return SsmAutomatePersister<STATE, ID, ENTITY>().also {
			it.ssmTxInitFunction = ssmTxInitFunction
			it.ssmSessionStartFunction = ssmSessionStartFunction
			it.ssmSessionPerformActionFunction = ssmSessionPerformActionFunction
			it.objectMapper = objectMapper
			it.dataSsmSessionGetQueryFunction = dataSsmSessionGetQueryFunction
			it.entityType = entityType()
			it.chaincodeUri = chaincodeUri()
			it.agentSigner = signerAgent()
			it.permisive = permisive
		}
	}

	abstract fun entityType(): Class<ENTITY>
	abstract fun chaincodeUri(): ChaincodeUri
	abstract fun signerAgent(): Agent

	open var permisive = false
}
