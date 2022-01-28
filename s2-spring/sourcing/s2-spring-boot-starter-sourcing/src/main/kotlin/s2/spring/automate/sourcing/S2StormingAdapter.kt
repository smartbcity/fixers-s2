package s2.spring.automate.sourcing

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.automate.core.TransitionStateGuard
import s2.automate.core.appevent.publisher.AutomateEventPublisher
import s2.automate.core.context.AutomateContext
import s2.automate.core.guard.Guard
import s2.automate.core.guard.GuardExecutorImpl
import s2.automate.sourcing.AutomateStormingExecutor
import s2.automate.sourcing.AutomateStormingExecutorImpl
import s2.automate.sourcing.automate.S2SourcingAutomate
import s2.dsl.automate.Evt
import s2.dsl.automate.S2State
import s2.sourcing.dsl.event.EventPersister
import s2.sourcing.dsl.event.SourcingProjectionBuilder
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.persister.SpringEventPublisher

abstract class S2StormingAdapter<ENTITY, STATE, EVENT, ID, EXECUTER> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
EVENT: Evt,
EVENT: WithS2Id<ID>,
EXECUTER : S2AutomateEvolverSpring<ENTITY, STATE, EVENT, ID> {

	@Bean
	open fun aggregate(
		eventPublisher: SpringEventPublisher,
		eventStore: EventPersister<EVENT, ID>,
		projectionBuilder: SourcingProjectionBuilder<ENTITY, EVENT, ID>
	): AutomateStormingExecutor<ENTITY, STATE, EVENT, ID> {
		val automateContext = automateContext()
		val publisher = automateAppEventPublisher(eventPublisher)
		val guardExecutor = guardExecutor(publisher)
		return AutomateStormingExecutorImpl(
			automateContext = automateContext,
			guardExecutor = guardExecutor,
			projectionBuilder = projectionBuilder,
			eventStore = eventStore,
			publisher = publisher
		)
	}

	protected open fun automateContext() = AutomateContext(automate())

	protected open fun guardExecutor(
		automateAppEventPublisher: AutomateEventPublisher<STATE, ID, ENTITY, S2SourcingAutomate>,
	): GuardExecutorImpl<STATE, ID, ENTITY, S2SourcingAutomate> {
		return GuardExecutorImpl(
			guards = guards(),
			publisher = automateAppEventPublisher
		)
	}

	protected open fun automateAppEventPublisher(eventPublisher: SpringEventPublisher)
			: AutomateEventPublisher<STATE, ID, ENTITY, S2SourcingAutomate> {
		return AutomateEventPublisher(eventPublisher)
	}

	protected open fun guards(): List<Guard<STATE, ID, ENTITY, S2SourcingAutomate>> = listOf(
		TransitionStateGuard()
	)

	@Configuration
	open inner class InitBean(
		private val automateStormingExecutor: AutomateStormingExecutor<ENTITY, STATE, EVENT, ID>
	): InitializingBean{
		override fun afterPropertiesSet() {
			val agg = executor()
			agg.withContext(automateStormingExecutor)
		}
	}

	abstract fun automate(): S2SourcingAutomate
	abstract fun executor(): EXECUTER
}
