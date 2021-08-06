package s2.dsl.automate

import f2.dsl.cqrs.Command

actual interface S2InitCommand : Command
actual interface S2Command<ID> : Command {
	actual val id: ID
}