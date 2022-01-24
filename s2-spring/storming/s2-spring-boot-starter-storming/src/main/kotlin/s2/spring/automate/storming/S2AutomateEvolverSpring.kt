package s2.spring.automate.storming

import kotlinx.coroutines.flow.map
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import s2.automate.storming.AutomateStormingExecutor
import s2.automate.storming.Decide
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2Event
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2State

open class S2AutomateEvolverSpring<STATE, ID, ENTITY, EVENT> : S2AutomateEvolver<STATE, ID, ENTITY, EVENT> where
STATE : S2State,
EVENT : S2Event<STATE, ID>,
ENTITY : WithS2State<STATE> {

	private lateinit var automateExecutor: AutomateStormingExecutor<STATE, ID, ENTITY, EVENT>


	internal fun withContext(automateExecutor: AutomateStormingExecutor<STATE, ID, ENTITY, EVENT>) {
		this.automateExecutor = automateExecutor
	}

	override suspend fun <EVENT_OUT : EVENT> init(command: S2InitCommand, buildEvent: suspend () -> EVENT_OUT): EVENT_OUT {
		return automateExecutor.init(command, buildEvent)
	}

	override suspend fun <EVENT_OUT : EVENT> transition(command: S2Command<ID>, exec: suspend (ENTITY) -> EVENT_OUT): EVENT_OUT {
		return automateExecutor.transition(command, exec)
	}

	fun <EVENT_OUT : EVENT, COMMAND: S2InitCommand> init(fnc: suspend (t: COMMAND) -> EVENT_OUT): Decide<ID, STATE, COMMAND, EVENT_OUT> =
		Decide { msg ->
			msg.map { cmd ->
				init(cmd) {
					fnc(cmd)
				}
			}
		}

	fun <EVENT_OUT : EVENT, COMMAND: S2Command<ID>> decide(fnc: suspend (t: COMMAND, entity: ENTITY) -> EVENT_OUT)
			: Decide<ID, STATE, COMMAND, EVENT_OUT> = Decide { msg ->
		msg.map { cmd ->
			transition(cmd) { model ->
				fnc(cmd, model)
			}
		}
	}

}
