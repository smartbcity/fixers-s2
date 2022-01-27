package s2.spring.automate.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.core.support.ReactiveRepositoryFactorySupport
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.sourcing.StormingEventSink
import s2.spring.automate.sourcing.entity.SnapEntityRepository

@EnableConfigurationProperties(S2AutomateProperties::class)
@ConditionalOnProperty(
	name = ["s2.sourcing.enable"],
	havingValue = "true")
@Configuration
open class StormingAutoconfiguration<STATE, ID, ENTITY> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {

	@Bean
	open fun snapEntityRepository(
		factory: ReactiveRepositoryFactorySupport,
	): SnapEntityRepository<STATE, ID, ENTITY> {
		return factory.getRepository(SnapEntityRepository::class.java) as SnapEntityRepository<STATE, ID, ENTITY>
	}

	@Bean
	open fun stormingEventSink(entity: SnapEntityRepository<STATE, ID, ENTITY>): StormingEventSink<STATE, ID, ENTITY> {
		return StormingEventSink(entity)
	}
}
