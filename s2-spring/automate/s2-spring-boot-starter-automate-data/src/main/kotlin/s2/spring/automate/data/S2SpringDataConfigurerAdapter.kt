package s2.spring.automate.data

import org.springframework.beans.factory.annotation.Autowired
import s2.automate.core.persist.AutotmatePersister
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.S2ConfigurerAdapter
import s2.spring.automate.persister.SpringDataAutomatePersister

abstract class S2SpringDataConfigurerAdapter<STATE, ID, ENTITY> : S2ConfigurerAdapter<STATE, ID, ENTITY>() where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {

	@Autowired
	lateinit var springDataAutomatePersister: SpringDataAutomatePersister<STATE, ID, ENTITY>

	override fun aggregateRepository(): AutotmatePersister<STATE, ID, ENTITY> {
		return springDataAutomatePersister
	}

}
