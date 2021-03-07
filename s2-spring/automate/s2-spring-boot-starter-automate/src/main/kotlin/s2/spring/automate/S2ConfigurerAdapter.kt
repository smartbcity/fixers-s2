package s2.spring.automate

import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2State
import s2.spring.automate.persister.SpringEventPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import s2.automate.core.AutomateExecutor
import s2.automate.core.AutomateExecutorCore
import s2.automate.core.guard.GuardExecutorImpl
import s2.automate.core.TransitionStateGuard
import s2.automate.core.appevent.publisher.AutomateAppEventPublisher
import s2.automate.core.context.AutomateContext
import s2.automate.core.persist.AutotmatePersister

abstract class S2ConfigurerAdapter<STATE, ID, ENTITY> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {

	@Bean
	open fun aggregate(
		automateContext: AutomateContext<STATE, ID, ENTITY>,
		guardExecutor: GuardExecutorImpl<STATE, ID, ENTITY>,
		persister: AutotmatePersister<STATE, ID, ENTITY>,
		publisher: AutomateAppEventPublisher<STATE, ID, ENTITY>
	): AutomateExecutor<STATE, ID, ENTITY> {
		return AutomateExecutorCore(automateContext,guardExecutor, persister, publisher)
	}

	@Autowired
	lateinit var publisher: ApplicationEventPublisher

	@Bean
	open fun automateContext(automate: S2Automate) = AutomateContext(automate, guards())

	@Bean
	open fun eventPublisher(): SpringEventPublisher {
		return SpringEventPublisher(publisher)
	}

	@Bean
	open fun guardExecutor(automateAppEventPublisher: AutomateAppEventPublisher<STATE, ID, ENTITY>): GuardExecutorImpl<STATE, ID, ENTITY> {
		return GuardExecutorImpl(
			guards = guards(),
			publisher = automateAppEventPublisher
		)
	}

	@Bean
	open fun automateAppEventPublisher(eventPublisher: SpringEventPublisher): AutomateAppEventPublisher<STATE, ID, ENTITY> {
		return AutomateAppEventPublisher(eventPublisher)
	}

	private fun guards() =
		listOf(TransitionStateGuard<STATE, ID, ENTITY>())

	abstract fun aggregateRepository(): AutotmatePersister<STATE, ID, ENTITY>

	@Bean
	abstract fun automate(): S2Automate

}