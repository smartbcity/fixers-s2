package city.smartb.s2.spring.aggregate.config

import city.smartb.s2.dsl.aggregate.S2AggregateBase
import city.smartb.s2.dsl.aggregate.entity.WithS2IdAndStatus
import city.smartb.s2.dsl.aggregate.event.EventPublisher
import city.smartb.s2.dsl.automate.S2Automate
import city.smartb.s2.dsl.automate.S2State
import city.smartb.s2.spring.aggregate.io.AggregateRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.reactive.ReactiveCrudRepository

@Configuration
open class S2DataAutoConfig {

	@Bean
	open fun <STATE: S2State, ID, ENTITY: WithS2IdAndStatus<ID, STATE>> aggregate(
			repository: ReactiveCrudRepository<ENTITY, ID>,
			eventPublisher: EventPublisher,
			automate: S2Automate
	): S2AggregateBase<STATE, ID, ENTITY> {
		return S2AggregateBase(automate, AggregateRepository(repository), eventPublisher)
	}

}