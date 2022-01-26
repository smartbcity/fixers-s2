package s2.spring.storming.data

import f2.dsl.cqrs.Event
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.core.support.ReactiveRepositoryFactorySupport
import s2.automate.storming.event.Evolver
import s2.automate.storming.event.StormingProjectionBuilder
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.storming.S2AutomateEvolverSpring
import s2.spring.automate.storming.S2StormingAdapter
import s2.spring.storming.data.event.EventPersisterData
import s2.spring.storming.data.event.SpringDataEventRepository

abstract class S2StormingSpringDataAdapter<ENTITY, STATE, EVENT, ID, EXECUTER>
	: S2StormingAdapter<ENTITY, STATE, EVENT, ID, EXECUTER>() where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
EVENT: Event,
EVENT: WithS2Id<ID>,
EXECUTER : S2AutomateEvolverSpring<ENTITY, STATE, EVENT, ID> {

	@Bean
	open fun stormingProjectionBuilder(
		eventStore: EventPersisterData<EVENT, ID>,
		evolver: Evolver<ENTITY, EVENT>
	): StormingProjectionBuilder<ENTITY, STATE, EVENT, ID> {
		return StormingProjectionBuilder(eventStore, evolver)
	}

	@Bean
	open fun eventStore(
		eventRepository: SpringDataEventRepository<EVENT, ID>
	): EventPersisterData<EVENT, ID> {
		return EventPersisterData(eventRepository)
	}


	@Bean
	open fun springDataEventRepository(repositoryFactorySupport: ReactiveRepositoryFactorySupport): SpringDataEventRepository<EVENT, ID> {
		return repositoryFactorySupport.getRepository(SpringDataEventRepository::class.java) as SpringDataEventRepository<EVENT, ID>
	}

}
