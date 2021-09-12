package s2.spring.automate.data.persister

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import s2.automate.core.context.InitTransitionAppliedContext
import s2.automate.core.context.TransitionAppliedContext
import s2.automate.core.persist.AutomatePersister
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State

class SpringDataAutomatePersister<STATE, ID, ENTITY>(
	private val repository: ReactiveCrudRepository<ENTITY, ID>,
) : AutomatePersister<STATE, ID, ENTITY> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {

	override suspend fun persist(
		transitionContext: TransitionAppliedContext<STATE, ID, ENTITY>,
	): ENTITY {
		return repository.save(transitionContext.entity).awaitSingle()
	}

	override suspend fun load(id: ID): ENTITY? {
		return repository.findById(id).awaitFirstOrNull()
	}

	override suspend fun persist(transitionContext: InitTransitionAppliedContext<STATE, ID, ENTITY>): ENTITY {
		return repository.save(transitionContext.entity).awaitSingle()
	}
}
