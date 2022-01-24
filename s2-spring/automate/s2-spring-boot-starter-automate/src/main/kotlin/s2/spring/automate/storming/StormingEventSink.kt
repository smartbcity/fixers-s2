package s2.spring.automate.storming

import org.springframework.context.event.EventListener
import s2.automate.core.appevent.AutomateInitTransitionEnded
import s2.automate.core.appevent.AutomateTransitionEnded
import s2.automate.core.appevent.listener.AutomateListenerAdapter
import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.spring.automate.storming.entity.SnapEntityRepository
import s2.spring.automate.storming.entity.StormingSnapEntity
import s2.spring.automate.storming.entity.StormingSnapTransaction

class StormingEventSink<STATE, ID, ENTITY>(
	private val repo: SnapEntityRepository<STATE, ID, ENTITY>,
) : AutomateListenerAdapter<STATE, ID, ENTITY, S2Automate<ID>>()
		where STATE : S2State, ENTITY : WithS2State<STATE>, ENTITY : WithS2Id<ID> {

	@EventListener
	override fun automateInitTransitionEnded(event: AutomateInitTransitionEnded<STATE, ID, ENTITY>) {
		val snapTransaction = StormingSnapTransaction(
			from = null,
			to = event.to,
			command = event.command,
			entity = event.entity
		)
		val entity = StormingSnapEntity(event.entity.s2Id(), event.entity.s2Id(), snapTransaction)
		saveSnap(entity)
	}

	@EventListener
	override fun automateTransitionEnded(event: AutomateTransitionEnded<STATE, ID, ENTITY>) {
		val snapTransaction = StormingSnapTransaction(
			from = event.from,
			to = event.to,
			command = event.command,
			entity = event.entity
		)
		val entity = StormingSnapEntity(event.entity.s2Id(), event.entity.s2Id(), snapTransaction)
		saveSnap(entity)
	}

	private fun saveSnap(entity: StormingSnapEntity<STATE, ID, ENTITY>) {
		println("[${StormingEventSink::class.qualifiedName}] Start saving in database => $entity")
		repo.save(entity).subscribe {
			println("[${StormingEventSink::class.qualifiedName}] End saving entity => $entity")
		}
	}
}
