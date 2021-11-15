package s2.spring.automate.data

import org.springframework.context.annotation.Bean
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import s2.automate.core.persist.AutomatePersister
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.S2ConfigurerAdapter
import s2.spring.automate.data.persister.SpringDataAutomateCoroutinePersister
import s2.spring.automate.executor.S2AutomateExecutorSpring

abstract class S2SpringDataSuspendConfigurerAdapter<STATE, ID, ENTITY, AGGREGATE>(
	private val aggregateRepository: CoroutineCrudRepository<ENTITY, ID>
) : S2ConfigurerAdapter<STATE, ID, ENTITY, AGGREGATE>() where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
AGGREGATE : S2AutomateExecutorSpring<STATE, ID, ENTITY> {

	@Bean
	override fun aggregateRepository(): AutomatePersister<STATE, ID, ENTITY> {
		return SpringDataAutomateCoroutinePersister(
			aggregateRepository
		)
	}

}
