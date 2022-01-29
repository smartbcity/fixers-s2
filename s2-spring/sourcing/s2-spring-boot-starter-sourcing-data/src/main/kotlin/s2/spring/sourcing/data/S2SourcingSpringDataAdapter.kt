package s2.spring.sourcing.data

import org.springframework.context.annotation.Bean
import org.springframework.data.repository.core.support.ReactiveRepositoryFactorySupport
import s2.dsl.automate.Evt
import s2.sourcing.dsl.event.Evolver
import s2.sourcing.dsl.event.SourcingProjectionBuilder
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.sourcing.S2AutomateDeciderSpring
import s2.spring.automate.sourcing.S2StormingAdapter
import s2.spring.sourcing.data.event.EventPersisterData
import s2.spring.sourcing.data.event.SpringDataEventRepository

abstract class S2SourcingSpringDataAdapter<ENTITY, STATE, EVENT, ID, EXECUTER>
	: S2StormingAdapter<ENTITY, STATE, EVENT, ID, EXECUTER>() where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
EVENT: Evt,
EVENT: WithS2Id<ID>,
EXECUTER : S2AutomateDeciderSpring<ENTITY, STATE, EVENT, ID> {

	@Bean
	open fun sourcingProjectionBuilder(
		eventStore: EventPersisterData<EVENT, ID>,
		evolver: Evolver<ENTITY, EVENT>
	): SourcingProjectionBuilder<ENTITY, EVENT, ID> {
		return SourcingProjectionBuilder(eventStore, evolver)
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
