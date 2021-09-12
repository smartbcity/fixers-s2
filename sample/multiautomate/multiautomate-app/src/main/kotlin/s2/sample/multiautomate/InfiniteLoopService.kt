package s2.sample.multiautomate

import f2.dsl.fnc.f2Function
import java.util.UUID
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import s2.sample.multiautomate.endable.EndableCreateCommand
import s2.sample.multiautomate.endable.EndableLoopState
import s2.sample.multiautomate.endable.EndableStepCommand
import s2.sample.multiautomate.infiniteloop.InfiniteLoopS2Aggregate
import s2.sample.multiautomate.infiniteloop.entity.InfiniteLoopEntity

@Service
class InfiniteLoopService(
	private val aggregate: InfiniteLoopS2Aggregate,
) {

	@Bean
	fun createInfinite() = f2Function<EndableCreateCommand, String> { command ->
		aggregate.createWithEvent(command) {
			val id = UUID.randomUUID().toString()
			InfiniteLoopEntity(id, 0, EndableLoopState.Running.position) to id
		}
	}

	@Bean
	fun stepInfinite() = f2Function<EndableStepCommand, String> { command ->
		aggregate.doTransition(command) {
//            this.copy(step = this.step + 1) to "${this.step}"
			this to "${this.step}"
		}
	}
}
