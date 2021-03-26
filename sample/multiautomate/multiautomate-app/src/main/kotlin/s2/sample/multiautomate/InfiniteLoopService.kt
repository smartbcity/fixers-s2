package s2.sample.multiautomate

import f2.function.spring.adapter.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import s2.sample.multiautomate.endable.*
import s2.sample.multiautomate.infiniteloop.InfiniteLoopS2Aggregate
import s2.sample.multiautomate.infiniteloop.InfiniteLoopState
import s2.sample.multiautomate.infiniteloop.entity.InfiniteLoopEntity
import java.util.*

@Service
class InfiniteLoopService(
    private val aggregate: InfiniteLoopS2Aggregate
) {

    @Bean
    fun createInfinite() = f2Function<EndableCreateCommand, String> { command ->
        aggregate.createWithEvent(command, InfiniteLoopState.Running) {
            val id = UUID.randomUUID().toString()
            InfiniteLoopEntity(id, 0, EndableLoopState.Running.position) to id
        }
    }

    @Bean
    fun stepInfinite() = f2Function<EndableStepCommand, String> { command ->
        aggregate.doTransition(command, InfiniteLoopState.Running) {
//            this.copy(step = this.step + 1) to "${this.step}"
            this to "${this.step}"
        }
    }

}