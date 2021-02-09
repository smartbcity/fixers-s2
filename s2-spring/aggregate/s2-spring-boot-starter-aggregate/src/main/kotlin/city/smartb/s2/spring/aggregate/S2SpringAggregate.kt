package city.smartb.s2.spring.aggregate

import city.smartb.s2.dsl.aggregate.S2AggregateBase
import city.smartb.s2.dsl.aggregate.entity.WithS2IdAndStatus
import city.smartb.s2.dsl.automate.S2Automate
import city.smartb.s2.dsl.automate.S2State
import city.smartb.s2.spring.aggregate.io.AggregateRepository
import city.smartb.s2.spring.aggregate.io.EventPublisher

class S2SpringAggregate<STATE: S2State, ID, ENTITY: WithS2IdAndStatus<ID, STATE>> (
	s2: S2Automate,
	repository: AggregateRepository<ENTITY, ID>,
	applicationEventPublisher: EventPublisher
): S2AggregateBase<STATE, ID, ENTITY>(s2, repository, applicationEventPublisher)
