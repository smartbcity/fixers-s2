package city.smartb.s2.spring.aggregate.snap

import city.smartb.s2.dsl.aggregate.S2AggregateSnapshotBase
import city.smartb.s2.dsl.aggregate.entity.FindByIdFetcher
import city.smartb.s2.dsl.aggregate.entity.WithS2IdAndStatus
import city.smartb.s2.dsl.aggregate.event.EventPublisher
import city.smartb.s2.dsl.automate.S2Automate
import city.smartb.s2.dsl.automate.S2State
import org.springframework.stereotype.Service

@Service
class S2SpringAggregate<STATE: S2State, ID, ENTITY: WithS2IdAndStatus<ID, STATE>> (
	s2: S2Automate,
	repository: FindByIdFetcher<ENTITY, ID>,
	applicationEventPublisher: EventPublisher
): S2AggregateSnapshotBase<STATE, ID, ENTITY>(s2, repository, applicationEventPublisher)
