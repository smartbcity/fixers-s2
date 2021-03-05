package s2.spring.automate.persister

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import s2.automate.core.context.InitTransitionContext
import s2.automate.core.context.TransitionContext
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.automate.core.persist.AutotmatePersister
import s2.dsl.automate.S2State

class AggregateAutomatePersister<STATE, ID, ENTITY>(
	private val repository: ReactiveCrudRepository<ENTITY, ID>,
) : AutotmatePersister<STATE, ID, ENTITY> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {

	override suspend fun persist(
		transitionContext: TransitionContext<STATE, ID, ENTITY>,
		entity: ENTITY
	): ENTITY {
		return repository.save(entity).awaitSingle()
	}

	override suspend fun load(id: ID): ENTITY? {
		return repository.findById(id).awaitFirstOrNull()
	}

	override suspend fun persist(transitionContext: InitTransitionContext<STATE, ID, ENTITY>, entity: ENTITY): ENTITY {
		return repository.save(entity).awaitSingle()
	}

}

