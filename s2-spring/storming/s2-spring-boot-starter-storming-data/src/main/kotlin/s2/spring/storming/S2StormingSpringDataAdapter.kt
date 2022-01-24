package s2.spring.storming

import org.springframework.beans.factory.BeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.core.support.ReactiveRepositoryFactorySupport
import s2.automate.storming.event.Evolver
import s2.automate.storming.event.StormingProjectionBuilder
import s2.dsl.automate.S2Event
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.storming.S2AutomateEvolverSpring
import s2.spring.automate.storming.S2StormingAdapter
import s2.spring.storming.event.EventStoreSpringData
import s2.spring.storming.event.SpringDataEventRepository

abstract class S2StormingSpringDataAdapter<STATE, ID, ENTITY, EVENT, EXECUTER>
	: S2StormingAdapter<STATE, ID, ENTITY, EXECUTER, EVENT>() where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
EVENT: S2Event<STATE, ID>,
EXECUTER : S2AutomateEvolverSpring<STATE, ID, ENTITY, EVENT> {

	@Bean
	open fun stormingProjectionBuilder(
		eventStore: EventStoreSpringData<EVENT, ID>,
		evolver: Evolver<ENTITY, EVENT>
	): StormingProjectionBuilder<ENTITY, EVENT, STATE, ID> {
		return StormingProjectionBuilder(eventStore, evolver)
	}

	@Bean
	open fun eventStore(
		eventRepository: SpringDataEventRepository<EVENT, ID>
	): EventStoreSpringData<EVENT, ID> {
		return EventStoreSpringData(eventRepository)
	}


	@Bean
	open fun springDataEventRepository(repositoryFactorySupport: ReactiveRepositoryFactorySupport): SpringDataEventRepository<EVENT, ID> {
		return repositoryFactorySupport.getRepository(SpringDataEventRepository::class.java) as SpringDataEventRepository<EVENT, ID>
	}

	@Bean
	open fun rt(
		beanFactory: BeanFactory
	): String {
		return "repositoryFactorySupport"
	}

}
