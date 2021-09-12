import f2.dsl.function.F2Function
import f2.function.spring.adapter.f2Function
import s2.dsl.automate.S2Command
import s2.dsl.automate.S2InitCommand
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.executor.S2AutomateExecutorSpring

class S2AutomateExecutorF2<STATE, ID, ENTITY>(
	private val executer: S2AutomateExecutorSpring<STATE, ID, ENTITY>,
) where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID> {


	fun <EVENT> createWithEvent(build: suspend () -> Pair<ENTITY, EVENT>): F2Function<S2InitCommand, EVENT> =
		f2Function { cmd ->
			executer.createWithEvent(cmd, build)
		}

	suspend fun <EVENT> createWithEvent(
		buildEvent: suspend ENTITY.() -> EVENT,
		buildEntity: suspend () -> ENTITY,
	): F2Function<S2InitCommand, EVENT> = f2Function { cmd ->
		executer.createWithEvent(cmd, buildEvent, buildEntity)
	}

	suspend fun <T> doTransition(exec: suspend ENTITY.() -> Pair<ENTITY, T>): F2Function<S2Command<ID>, T> =
		f2Function { cmd ->
			executer.doTransition(cmd, exec)
		}

}
