package city.smartb.s2.spring.aggregate

import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.automate.S2State
import city.smartb.s2.spring.aggregate.io.AggregateRepository
import city.smartb.s2.spring.aggregate.io.EventPublisher
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.reactive.ReactiveCrudRepository

abstract class S2ConfigurerAdapter<STATE: S2State, ID, ENTITY: WithS2State<STATE>>  {

	@Autowired
	lateinit var publisher: ApplicationEventPublisher

	@Bean
	open fun aggregateRepository() = AggregateRepository(repository())

	@Bean
	open fun eventPublisher(): EventPublisher {
		return EventPublisher(publisher)
	}

	@Bean
	open fun objectMapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())

	abstract fun repository(): ReactiveCrudRepository<ENTITY, ID>

}