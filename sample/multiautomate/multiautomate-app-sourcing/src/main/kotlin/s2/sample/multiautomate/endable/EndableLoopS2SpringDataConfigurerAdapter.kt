package s2.sample.multiautomate.endable

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.sample.multiautomate.endable.entity.EndableLoopEntity
import s2.sourcing.dsl.view.View
import s2.spring.automate.sourcing.S2AutomateDeciderSpring
import s2.spring.sourcing.data.S2SourcingSpringDataAdapter

@Configuration
class EndableLoopS2SpringDataConfigurerAdapter(endableLoopS2Aggregate: EndableLoopS2Aggregate) : S2SourcingSpringDataAdapter<
		EndableLoopEntity, EndableLoopState, EndableLoopEvent, EndableLoopId, EndableLoopS2Aggregate
>(endableLoopS2Aggregate) {
	override fun automate() = endableLoopS2()

	@Autowired
	lateinit var endableLoopS2Aggregate: EndableLoopS2Aggregate

//	override fun executor(): EndableLoopS2Aggregate = endableLoopS2Aggregate

	override fun view(): View<EndableLoopEvent, EndableLoopEntity> {
		TODO("Not yet implemented")
	}
}

@Service
class EndableLoopS2Aggregate : S2AutomateDeciderSpring<EndableLoopEntity, EndableLoopState, EndableLoopEvent, EndableLoopId>()
