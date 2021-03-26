package s2.sample.multiautomate.endable

import s2.dsl.automate.builder.s2
import s2.dsl.automate.*

typealias EndableLoopId = String

fun EndableLoopS2(): S2Automate {
	return s2<EndableLoopId, EndableLoopState> {
		name = "EndableLoopS2"
		init<EndableCreateCommand> {
			to = EndableLoopState.Running
			role = EndableLoopRole.User()
			cmd = EndableCreateCommand::class
		}
		transaction<EndableStepCommand> {
			from = EndableLoopState.Running
			to = EndableLoopState.Running
			role = EndableLoopRole.User()
			cmd = EndableStepCommand::class
		}
		transaction<EndableEndCommand> {
			from = EndableLoopState.Running
			to = EndableLoopState.Ended
			role = EndableLoopRole.User()
			cmd = EndableEndCommand::class
		}
	}
}

sealed class EndableLoopRole : S2Role {
	class User : EndableLoopRole()

	override fun toString(): String {
		return this::class.simpleName!!
	}
}

open class EndableLoopState(
	override val position: Int,
) : S2State {
	object Running : EndableLoopState(0)
	object Ended : EndableLoopState(1)

	override fun toString(): String {
		return this::class.simpleName!!
	}
}

interface EndableLoopInitCommand : S2InitCommand
interface EndableLoopCommand : S2Command<EndableLoopId>
interface EndableLoopEvent : S2Event<EndableLoopState, EndableLoopId>


class EndableCreateCommand(
	val tt: String
): EndableLoopInitCommand
class EndableStepCommand(override val id: EndableLoopId): EndableLoopCommand
class EndableEndCommand(override val id: EndableLoopId): EndableLoopCommand
