package s2.sample.multiautomate

import org.springframework.stereotype.Service
import s2.sample.multiautomate.endable.EndableLoopS2Aggregate

@Service
class EndableLoopService(
	private val endableLoopS2Aggregate: EndableLoopS2Aggregate,
)
