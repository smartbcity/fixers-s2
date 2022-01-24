package s2.dsl.automate

import f2.dsl.cqrs.Command

expect interface S2InitCommand : Command

expect interface S2Command<ID> : Command, WithId<ID> {
	override val id: ID
}
