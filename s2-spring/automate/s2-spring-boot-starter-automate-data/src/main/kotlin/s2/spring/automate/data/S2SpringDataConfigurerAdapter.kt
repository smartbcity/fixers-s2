package s2.spring.automate.data

import org.springframework.context.annotation.Bean
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import s2.automate.core.persist.AutotmatePersister
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.S2ConfigurerAdapter
import s2.spring.automate.S2SpringAggregate
import s2.spring.automate.data.persister.SpringDataAutomatePersister

abstract class S2SpringDataConfigurerAdapter<STATE, ID, ENTITY, AGGREGATE>(
	private val aggregateRepository: ReactiveSortingRepository<ENTITY, ID>
) : S2ConfigurerAdapter<STATE, ID, ENTITY, AGGREGATE>() where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
AGGREGATE : S2SpringAggregate<STATE, ID, ENTITY> {

	override fun aggregateRepository(): AutotmatePersister<STATE, ID, ENTITY> {
		return SpringDataAutomatePersister(
			aggregateRepository
		)
	}

}
