package s2.dsl.automate

actual interface S2InitCommand : Cmd
actual interface S2Command<ID> : Cmd, WithId<ID> {
	actual override val id: ID
}
