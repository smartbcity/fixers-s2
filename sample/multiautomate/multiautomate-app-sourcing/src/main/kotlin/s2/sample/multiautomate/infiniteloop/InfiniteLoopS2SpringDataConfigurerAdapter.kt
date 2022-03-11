package s2.sample.multiautomate.infiniteloop

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.sample.multiautomate.infiniteloop.entity.InfiniteLoopEntity
import s2.sourcing.dsl.view.View
import s2.spring.automate.sourcing.S2AutomateDeciderSpring
import s2.spring.sourcing.data.S2SourcingSpringDataAdapter

@Configuration
class ApplicationS2SpringDataConfigurerAdapter(
	infiniteLoopS2Aggregate: InfiniteLoopS2Aggregate
) : S2SourcingSpringDataAdapter<
		InfiniteLoopEntity, InfiniteLoopState, InfiniteLoopEvent, InfiniteLoopId, InfiniteLoopS2Aggregate
		>(infiniteLoopS2Aggregate) {
	override fun automate() = infiniteLoopS2()

	override fun view(): View<InfiniteLoopEvent, InfiniteLoopEntity> {
		TODO("Not yet implemented")
	}
}

@Service
class InfiniteLoopS2Aggregate : S2AutomateDeciderSpring<InfiniteLoopEntity, InfiniteLoopState, InfiniteLoopEvent, InfiniteLoopId>()
