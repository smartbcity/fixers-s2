package s2.spring.automate.storming

import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import s2.automate.core.appevent.publisher.AutomateEventPublisher
import s2.automate.core.context.AutomateContext
import s2.automate.core.guard.GuardAdapter
import s2.automate.core.guard.GuardExecutorImpl
import s2.automate.storming.AutomateStormingExecutorImpl
import s2.automate.storming.AutomateStormingExecutor
import s2.automate.storming.event.EventPersister
import s2.automate.storming.event.StormingProjectionBuilder
import s2.dsl.automate.S2Event
import s2.dsl.automate.S2State
import s2.dsl.automate.event.S2StormingAutomate
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.persister.SpringEventPublisher

abstract class S2StormingAdapter<STATE, ID, ENTITY, EXECUTER, EVENT> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
EVENT: S2Event<STATE, ID>,
EXECUTER : S2AutomateEvolverSpring<STATE, ID, ENTITY, EVENT> {

	@Autowired
	private lateinit var eventPublisher: SpringEventPublisher

	@Bean
	open fun aggregate(
		eventStore: EventPersister<EVENT, ID>,
		projectionBuilder: StormingProjectionBuilder<ENTITY, EVENT, STATE, ID>
	): AutomateStormingExecutor<STATE, ID, ENTITY, EVENT> {
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

	protected open fun automateContext() = AutomateContext(automate(), guards())

	protected open fun guardExecutor(
		automateAppEventPublisher: AutomateEventPublisher<STATE, ID, ENTITY, S2StormingAutomate<ID>>,
	): GuardExecutorImpl<STATE, ID, ENTITY, S2StormingAutomate<ID>> {
		return GuardExecutorImpl(
			guards = guards(),
			publisher = automateAppEventPublisher
		)
	}

	protected open fun automateAppEventPublisher(eventPublisher: SpringEventPublisher)
			: AutomateEventPublisher<STATE, ID, ENTITY, S2StormingAutomate<ID>> {
		return AutomateEventPublisher(eventPublisher)
	}

	protected open fun guards(): List<GuardAdapter<STATE, ID, ENTITY, S2StormingAutomate<ID>>> = listOf(
//		TransitionStateGuard()
	)

	@Configuration
	open inner class InitBean(
		private val automateStormingExecutor: AutomateStormingExecutor<STATE, ID, ENTITY, EVENT>
	): InitializingBean{
		override fun afterPropertiesSet() {
			val agg = executor()
			agg.withContext(automateStormingExecutor)
		}
	}

	abstract fun automate(): S2StormingAutomate<ID>
	abstract fun executor(): EXECUTER
}
