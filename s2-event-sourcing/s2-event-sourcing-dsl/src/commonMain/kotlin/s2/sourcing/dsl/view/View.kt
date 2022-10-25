package s2.sourcing.dsl.view

import s2.dsl.automate.Evt

interface View<EVENT, ENTITY>
where EVENT: Evt
{
	suspend fun evolve(event: EVENT, model: ENTITY?): ENTITY?
}
