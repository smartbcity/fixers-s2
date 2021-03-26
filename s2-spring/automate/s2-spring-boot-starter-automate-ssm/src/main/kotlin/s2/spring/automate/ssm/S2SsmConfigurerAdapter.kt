package s2.spring.automate.ssm

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import s2.automate.core.persist.AutomatePersister
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.S2ConfigurerAdapter
import s2.spring.automate.executer.S2AutomateExecuterSpring
import s2.spring.automate.ssm.config.S2SsmProperties
import s2.spring.automate.ssm.persister.SsmAutomatePersister
import ssm.client.domain.Signer
import ssm.client.domain.SignerAdmin
import ssm.dsl.query.SsmGetSessionFunction
import ssm.f2.SsmSessionPerformActionFunction
import ssm.f2.SsmSessionStartFunction
import java.lang.Exception

@EnableConfigurationProperties(S2SsmProperties::class)
abstract class S2SsmConfigurerAdapter<STATE, ID, ENTITY, AGGREGATE> : InitializingBean, S2ConfigurerAdapter<STATE, ID, ENTITY, AGGREGATE>() where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
AGGREGATE : S2AutomateExecuterSpring<STATE, ID, ENTITY> {

	@Autowired
	lateinit var ssmAutomatePersister: SsmAutomatePersister<STATE, ID, ENTITY>

	@Bean
	open fun signer(s2SsmProperties: S2SsmProperties): Signer {
		return s2SsmProperties.getSigner()
	}

	@Bean
	open fun signerAdmin(s2SsmProperties: S2SsmProperties): SignerAdmin {
		return s2SsmProperties.getSignerAdmin()
	}

	@Bean
	open  fun ssmAutomatePersister(
		ssmSessionStartFunction: SsmSessionStartFunction,
		ssmSessionPerformActionFunction: SsmSessionPerformActionFunction,
		ssmGetSessionFunction: SsmGetSessionFunction,
		signer: Signer,
		signerAdmin: SignerAdmin,
		objectMapper: ObjectMapper,
		config: S2SsmProperties
	): SsmAutomatePersister<*, *, *> {
		return SsmAutomatePersister<STATE, ID, ENTITY>(ssmSessionStartFunction, ssmSessionPerformActionFunction, ssmGetSessionFunction, signer, signerAdmin, objectMapper, config)
	}

	override fun aggregateRepository(): AutomatePersister<STATE, ID, ENTITY> {
		return ssmAutomatePersister
	}

	@Throws(Exception::class)
	override fun afterPropertiesSet() {
		super.afterPropertiesSet()
		ssmAutomatePersister.entityType = entityType()
	}

	abstract fun entityType(): Class<ENTITY>

}
