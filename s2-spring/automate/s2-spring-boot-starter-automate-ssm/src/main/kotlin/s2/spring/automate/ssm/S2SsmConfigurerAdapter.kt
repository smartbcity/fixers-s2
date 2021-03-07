package s2.spring.automate.ssm

import com.fasterxml.jackson.core.type.TypeReference
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import s2.automate.core.persist.AutotmatePersister
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.S2ConfigurerAdapter
import s2.spring.automate.ssm.config.S2SsmProperties
import s2.spring.automate.ssm.persister.SsmAutomatePersister
import java.lang.Exception


@EnableConfigurationProperties(S2SsmProperties::class)
abstract class S2SsmConfigurerAdapter<STATE, ID, ENTITY> : InitializingBean, S2ConfigurerAdapter<STATE, ID, ENTITY>() where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {

	@Autowired
	lateinit var ssmAutomatePersister: SsmAutomatePersister<STATE, ID, ENTITY>

	override fun aggregateRepository(): AutotmatePersister<STATE, ID, ENTITY> {
		return ssmAutomatePersister
	}

	@Throws(Exception::class)
	override fun afterPropertiesSet() {
		ssmAutomatePersister.entityType = entityType()
	}

	abstract fun entityType(): Class<ENTITY>

}
