package s2.sample.multiautomate

import org.springframework.stereotype.Service
import s2.sample.multiautomate.infiniteloop.InfiniteLoopS2Aggregate

@Service
class InfiniteLoopService(
	private val aggregate: InfiniteLoopS2Aggregate,
) {

//	@Bean
//	fun createInfinite() = f2Function<EndableCreateCommand, String> { command ->
//		aggregate.createWithEvent(command) {
//			val id = UUID.randomUUID().toString()
//			InfiniteLoopEntity(id, 0, EndableLoopState.Running.position) to id
//		}
//	}
//
//	@Bean
//	fun stepInfinite() = f2Function<EndableStepCommand, String> { command ->
//		aggregate.doTransition(command) {
////            this.copy(step = this.step + 1) to "${this.step}"
//			this to "${this.step}"
//		}
//	}
}
