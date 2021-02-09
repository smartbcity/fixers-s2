package city.smartb.s2.spring.aggregate.snap

import city.smartb.s2.dsl.aggregate.S2AggregateSnapshotBase
import city.smartb.s2.dsl.aggregate.entity.WithS2Id
import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.aggregate.event.EventPublisher
import city.smartb.s2.dsl.aggregate.event.StateSnapAppEvent
import city.smartb.s2.dsl.automate.S2Automate
import city.smartb.s2.dsl.automate.S2State
import city.smartb.s2.spring.aggregate.snap.entity.SnapEntityRepository
import city.smartb.s2.spring.aggregate.snap.io.AggregateRepository
import city.smartb.s2.spring.aggregate.snap.io.AppEventPublisher
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.data.repository.core.support.ReactiveRepositoryFactorySupport
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

abstract class S2ConfigurerAdapter<STATE, ID, ENTITY>
		where STATE: S2State, ENTITY : WithS2State<STATE>, ENTITY: WithS2Id<ID> {

	@Autowired
	lateinit var publisher: org.springframework.context.ApplicationEventPublisher

	@Bean
	open fun cloudEventEntityRepository(
		factory: ReactiveRepositoryFactorySupport,
	): SnapEntityRepository<STATE, ID, ENTITY> {
		return factory.getRepository(SnapEntityRepository::class.java) as SnapEntityRepository<STATE, ID, ENTITY>
	}

	@Bean
	open fun eventPublisher(): EventPublisher {
		return AppEventPublisher(publisher)
	}

	@Bean
	open fun snapCommandCloudEventSink(
		repository: SnapEntityRepository<STATE, ID, ENTITY>
	): SnapCommandCloudEventSink<STATE, ID, ENTITY> {
		return SnapCommandCloudEventSink(repository)
	}

	@Bean
	open fun s2AggregateSnapshotBase(
		eventPublisher: EventPublisher,
		aggregateRepository: AggregateRepository<STATE, ID, ENTITY>
	): S2AggregateSnapshotBase<STATE, ID, ENTITY> {
		return S2AggregateSnapshotBase(
			automate(),
			aggregateRepository,
			eventPublisher
		)
	}

	@Bean
	open fun aggregateRepository(
		repository: SnapEntityRepository<STATE, ID, ENTITY>
	) = AggregateRepository(repository)

	@Bean
	open fun fetchAllSnapEntity(repo: SnapEntityRepository<STATE, ID, ENTITY>): () -> Flux<StateSnapAppEvent<STATE, ID, ENTITY>> {
		return { repo.findAll().map { it.event } }
	}

	@Bean
	open fun fetchAllLastSnapEntity(repo: SnapEntityRepository<STATE, ID, ENTITY>): () -> Mono<StateSnapAppEvent<STATE, ID, ENTITY>> {
		return { repo.findAll().map { it.event }.next() }
	}

	@Bean
	open fun fetchAllSnapEntityById(repo: SnapEntityRepository<STATE, ID, ENTITY>): (ID) -> Flux<StateSnapAppEvent<STATE, ID, ENTITY>> {
		return { id -> repo.findByEntityId(id).map { it.event } }
	}

	@Bean
	open fun objectMapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())

	abstract fun automate(): S2Automate

}