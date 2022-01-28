package s2.dsl.automate

expect interface S2InitCommand : Cmd

expect interface S2Command<ID> : Cmd, WithId<ID> {
	override val id: ID
}
