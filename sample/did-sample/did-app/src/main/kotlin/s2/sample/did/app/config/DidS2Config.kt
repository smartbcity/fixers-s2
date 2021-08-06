package s2.sample.did.app.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.dsl.automate.S2Automate
import s2.sample.did.app.entity.DidEntity
import s2.sample.did.domain.DidId
import s2.sample.did.domain.DidState
import s2.sample.did.domain.didS2
import s2.spring.automate.executor.S2AutomateExecutorSpring
import s2.spring.automate.ssm.S2SsmConfigurerAdapter

@Configuration
class DidS2Config: S2SsmConfigurerAdapter<DidState, String, DidEntity, DidS2Aggregate>() {

//	@Bean
	override fun automate(): S2Automate {
		return didS2()
	}

	override fun entityType(): Class<DidEntity> {
		return DidEntity::class.java
	}


	@Autowired
	lateinit var didS2Aggregate: DidS2Aggregate

	override fun executor(): DidS2Aggregate = didS2Aggregate

}

@Service
class DidS2Aggregate : S2AutomateExecutorSpring<DidState, DidId, DidEntity>()
