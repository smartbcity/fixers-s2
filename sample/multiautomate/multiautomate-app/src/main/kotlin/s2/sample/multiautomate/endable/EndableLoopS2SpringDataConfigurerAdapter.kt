package s2.sample.multiautomate.endable

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import s2.sample.multiautomate.endable.entity.EndableLoopEntity
import s2.sample.multiautomate.endable.entity.EndableLoopRepository
import s2.spring.automate.executer.S2AutomateExecuterSpring
import s2.spring.automate.data.S2SpringDataConfigurerAdapter

@Configuration
class EndableLoopS2SpringDataConfigurerAdapter(
	repository: EndableLoopRepository
): S2SpringDataConfigurerAdapter<EndableLoopState, EndableLoopId, EndableLoopEntity, EndableLoopS2Aggregate>(repository) {
	override fun automate() = EndableLoopS2()

	@Autowired
	lateinit var endableLoopS2Aggregate: EndableLoopS2Aggregate

	override fun s2SpringAggregate(): EndableLoopS2Aggregate = endableLoopS2Aggregate
}

@Service
class EndableLoopS2Aggregate : S2AutomateExecuterSpring<EndableLoopState, EndableLoopId, EndableLoopEntity>()
