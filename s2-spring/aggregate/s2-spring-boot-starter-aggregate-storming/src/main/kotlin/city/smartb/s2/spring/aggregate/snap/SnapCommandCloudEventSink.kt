package city.smartb.s2.spring.aggregate.snap

import city.smartb.s2.dsl.aggregate.entity.WithS2Id
import city.smartb.s2.dsl.aggregate.entity.WithS2State
import city.smartb.s2.dsl.aggregate.event.StateSnapAppEvent
import city.smartb.s2.dsl.automate.S2State
import city.smartb.s2.spring.aggregate.snap.entity.SnapEntity
import city.smartb.s2.spring.aggregate.snap.entity.SnapEntityRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.runBlocking
import org.springframework.context.event.EventListener

class SnapCommandCloudEventSink<STATE, ID, ENTITY>(
	private val repo: SnapEntityRepository<STATE, ID, ENTITY>,
)
		where STATE: S2State, ENTITY : WithS2State<STATE>, ENTITY: WithS2Id<ID> {

	@EventListener
	fun storeCommand(event: StateSnapAppEvent<STATE, ID, ENTITY>) = runBlocking {
		println("[SnapCommandCloudEventSink] Start saving in database => ${event}")
		val entity = SnapEntity(event.entity.s2Id(), event.entity.s2Id(),event)
		repo.save(entity).awaitFirst()
		println("[SnapCommandCloudEventSink] End saving entity => ${event}")
	}

}