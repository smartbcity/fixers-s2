package s2.sample.multiautomate.infiniteloop

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.sample.multiautomate.endable.EndableLoopEvent
import s2.sample.multiautomate.endable.entity.EndableLoopEntity
import s2.sample.multiautomate.infiniteloop.entity.InfiniteLoopEntity
import s2.sourcing.dsl.view.View
import s2.spring.automate.sourcing.S2AutomateDeciderSpring
import s2.spring.sourcing.data.S2SourcingSpringDataAdapter

@Configuration
class ApplicationS2SpringDataConfigurerAdapter(
	infiniteLoopS2Aggregate: InfiniteLoopS2Aggregate,
	val infiniteLoopS2View: InfiniteLoopS2View
) : S2SourcingSpringDataAdapter<
		InfiniteLoopEntity, InfiniteLoopState, InfiniteLoopEvent, InfiniteLoopId, InfiniteLoopS2Aggregate
		>(infiniteLoopS2Aggregate) {

	override fun automate() = infiniteLoopS2()

	override fun view() = infiniteLoopS2View
}

@Service
class InfiniteLoopS2Aggregate : S2AutomateDeciderSpring<InfiniteLoopEntity, InfiniteLoopState, InfiniteLoopEvent, InfiniteLoopId>()

@Service
class InfiniteLoopS2View : View<InfiniteLoopEvent, InfiniteLoopEntity> {
	override suspend fun evolve(event: InfiniteLoopEvent, model: InfiniteLoopEntity?): InfiniteLoopEntity? {
		TODO("Not yet implemented")
	}

}
