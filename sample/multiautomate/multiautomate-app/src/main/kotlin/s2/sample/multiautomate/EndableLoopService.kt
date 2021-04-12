package s2.sample.multiautomate

import f2.function.spring.adapter.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import s2.sample.multiautomate.endable.*
import s2.sample.multiautomate.endable.entity.EndableLoopEntity
import java.util.*

@Service
class EndableLoopService(
    private val endableLoopS2Aggregate: EndableLoopS2Aggregate
) {

    @Bean
    fun createEndable() = f2Function<EndableCreateCommand, String> { command ->
        endableLoopS2Aggregate.createWithEvent(command) {
            val id = UUID.randomUUID().toString()
            EndableLoopEntity(id, 0, EndableLoopState.Running.position) to id
        }
    }

    @Bean
    fun stepEndable() = f2Function<EndableStepCommand, String> { command ->
        endableLoopS2Aggregate.doTransition(command) {
            this.step = this.step + 1
           this to "${this.step}"
        }
    }

    @Bean
    fun endEndable() = f2Function<EndableEndCommand, String> { command ->
        endableLoopS2Aggregate.doTransition(command) {
            this.step = this.step + 1
            this.state = EndableLoopState.Ended.position
            this to "${this.step}"
        }
    }
}