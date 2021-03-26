package s2.sample.multiautomate.infiniteloop

import s2.dsl.automate.builder.s2
import s2.dsl.automate.*

typealias InfiniteLoopId = String

fun InfiniteLoopS2(): S2Automate {
	return s2<InfiniteLoopId, InfiniteLoopState> {
		name = "InfiniteLoopS2"
		init<InfiniteLoopInitCommand> {
			to = InfiniteLoopState.Running
			role = InfiniteLoopRole.User()
			cmd = InfiniteCreateCommand::class
		}
		transaction<InfiniteLoopCommand> {
			from = InfiniteLoopState.Running
			to = InfiniteLoopState.Running
			role = InfiniteLoopRole.User()
			cmd = InfiniteStepCommand::class
		}
	}
}

sealed class InfiniteLoopRole : S2Role {
	class User : InfiniteLoopRole()

	override fun toString(): String {
		return this::class.simpleName!!
	}
}

open class InfiniteLoopState(
	override val position: Int
	) : S2State {

	object Running : InfiniteLoopState(0)

	override fun toString(): String {
		return this::class.simpleName!!
	}
}

interface InfiniteLoopInitCommand : S2InitCommand
interface InfiniteLoopCommand: S2Command<InfiniteLoopId>
interface InfiniteLoopEvent : S2Event<InfiniteLoopState, InfiniteLoopId>

class InfiniteCreateCommand(): InfiniteLoopInitCommand
class InfiniteStepCommand(override val id: InfiniteLoopId): InfiniteLoopCommand