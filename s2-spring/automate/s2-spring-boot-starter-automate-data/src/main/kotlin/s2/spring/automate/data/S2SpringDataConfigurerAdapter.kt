package s2.spring.automate.data

import org.springframework.data.repository.reactive.ReactiveSortingRepository
import s2.automate.core.persist.AutomatePersister
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.S2ConfigurerAdapter
import s2.spring.automate.executer.S2AutomateExecuterSpring
import s2.spring.automate.data.persister.SpringDataAutomatePersister

abstract class S2SpringDataConfigurerAdapter<STATE, ID, ENTITY, AGGREGATE>(
	private val aggregateRepository: ReactiveSortingRepository<ENTITY, ID>
) : S2ConfigurerAdapter<STATE, ID, ENTITY, AGGREGATE>() where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
AGGREGATE : S2AutomateExecuterSpring<STATE, ID, ENTITY> {

	override fun aggregateRepository(): AutomatePersister<STATE, ID, ENTITY> {
		return SpringDataAutomatePersister(
			aggregateRepository
		)
	}

}
