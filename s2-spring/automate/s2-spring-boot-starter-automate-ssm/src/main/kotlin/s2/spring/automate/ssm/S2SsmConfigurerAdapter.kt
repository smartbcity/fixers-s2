package s2.spring.automate.ssm

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import s2.automate.core.persist.AutomatePersister
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.S2ConfigurerAdapter
import s2.spring.automate.executor.S2AutomateExecutorSpring
import s2.spring.automate.ssm.persister.SsmAutomatePersister
import ssm.chaincode.dsl.model.uri.ChaincodeUri
import ssm.data.dsl.features.query.DataSsmSessionGetQueryFunction
import ssm.sdk.sign.SignerUserProvider
import ssm.tx.dsl.features.ssm.SsmTxSessionPerformActionFunction
import ssm.tx.dsl.features.ssm.SsmTxSessionStartFunction

abstract class S2SsmConfigurerAdapter<STATE, ID, ENTITY, AGGREGATE> : InitializingBean,
	S2ConfigurerAdapter<STATE, ID, ENTITY, AGGREGATE>() where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
AGGREGATE : S2AutomateExecutorSpring<STATE, ID, ENTITY> {

	@Autowired
	lateinit var ssmAutomatePersister: SsmAutomatePersister<STATE, ID, ENTITY>

	@Bean
	open fun ssmAutomatePersister(
		ssmSessionStartFunction: SsmTxSessionStartFunction,
		ssmSessionPerformActionFunction: SsmTxSessionPerformActionFunction,
		dataSsmSessionGetQueryFunction: DataSsmSessionGetQueryFunction,
		signerUserProvider: SignerUserProvider,
		objectMapper: ObjectMapper,
	): SsmAutomatePersister<STATE, ID, ENTITY> {
		return SsmAutomatePersister(
			ssmSessionStartFunction = ssmSessionStartFunction,
			ssmSessionPerformActionFunction = ssmSessionPerformActionFunction,
			objectMapper = objectMapper,
			dataSsmSessionGetQueryFunction = dataSsmSessionGetQueryFunction,
			signerUserProvider = signerUserProvider,
		)
	}

	override fun aggregateRepository(): AutomatePersister<STATE, ID, ENTITY> {
		return ssmAutomatePersister
	}

	@Throws(Exception::class)
	override fun afterPropertiesSet() {
		super.afterPropertiesSet()
		ssmAutomatePersister.entityType = entityType()
		ssmAutomatePersister.chaincodeUri = chaincodeUri()
	}

	abstract fun entityType(): Class<ENTITY>
	abstract fun chaincodeUri(): ChaincodeUri
}
