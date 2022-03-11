package s2.sample.multiautomate

import f2.dsl.fnc.f2Function
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import s2.sample.multiautomate.endable.EndableCreateCommand
import s2.sample.multiautomate.endable.EndableEndCommand
import s2.sample.multiautomate.endable.EndableLoopS2Aggregate
import s2.sample.multiautomate.endable.EndableLoopState
import s2.sample.multiautomate.endable.EndableStepCommand
import s2.sample.multiautomate.endable.entity.EndableLoopEntity
import java.util.UUID

@Service
class EndableLoopService(
	private val endableLoopS2Aggregate: EndableLoopS2Aggregate,
) {

}
