package s2.automate.core.persist

import s2.automate.core.context.InitTransitionContext
import s2.automate.core.context.TransitionContext
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.dsl.automate.S2State

interface AutotmatePersister<STATE, ID, ENTITY> where
	STATE : S2State,
	ENTITY : WithS2State<STATE>,
	ENTITY : WithS2Id<ID>
{
	suspend fun persist(transitionContext: InitTransitionContext<STATE, ID, ENTITY>, entity: ENTITY): ENTITY
	suspend fun persist(transitionContext: TransitionContext<STATE, ID, ENTITY>, entity: ENTITY): ENTITY
	suspend fun load(id: ID): ENTITY?
}