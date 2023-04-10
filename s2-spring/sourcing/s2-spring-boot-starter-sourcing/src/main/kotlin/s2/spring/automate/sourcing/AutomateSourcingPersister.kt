package s2.spring.automate.sourcing

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.springframework.dao.OptimisticLockingFailureException
import s2.automate.core.context.AutomateContext
import s2.automate.core.context.InitTransitionAppliedContext
import s2.automate.core.context.TransitionAppliedContext
import s2.automate.core.error.ERROR_ENTITY_NOT_FOUND
import s2.automate.core.error.asException
import s2.automate.core.persist.AutomatePersister
import s2.dsl.automate.Evt
import s2.dsl.automate.S2Automate
import s2.dsl.automate.S2State
import s2.dsl.automate.model.WithS2Id
import s2.dsl.automate.model.WithS2State
import s2.sourcing.dsl.Loader
import s2.sourcing.dsl.event.EventRepository
import s2.sourcing.dsl.snap.SnapRepository

class AutomateSourcingPersister<STATE, ID, ENTITY, EVENT>(
    private val projectionLoader: Loader<EVENT, ENTITY, ID>,
    private val eventStore: EventRepository<EVENT, ID>,
    private val snapRepository: SnapRepository<ENTITY, ID>?,
    private val preventOptimisticLocking: Boolean,
) : AutomatePersister<STATE, ID, ENTITY, EVENT, S2Automate> where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
EVENT: Evt,
EVENT: WithS2Id<ID> {

    private val snapPersistChannel = AutomateSourcingPersisterSnapChannel(persist = ::persistSnap)

    override suspend fun persist(transitionContext: InitTransitionAppliedContext<STATE, ID, ENTITY, EVENT, S2Automate>): ENTITY {
        val event = transitionContext.event
        val id = transitionContext.entity.s2Id()
        return persist(id, event)
    }

    override suspend fun persist(transitionContext: TransitionAppliedContext<STATE, ID, ENTITY, EVENT, S2Automate>): ENTITY {
        val event = transitionContext.event
        val id = transitionContext.entity.s2Id()
        return persist(id, event)
    }

    private suspend fun persist(id: ID, event: EVENT): ENTITY {
        val entity = if (preventOptimisticLocking) {
            snapPersistChannel.addToPersistQueue(id, event)
        } else {
            persistSnap(id, event)
        }
        eventStore.persist(event)
        return entity
    }
    private suspend fun persistSnap(id: ID, event: EVENT): ENTITY {
        val entityMutated = projectionLoader.loadAndEvolve(id, flowOf(event))
            ?: throw ERROR_ENTITY_NOT_FOUND(event.s2Id().toString()).asException()
        return snapRepository?.save(entityMutated) ?: entityMutated
    }

    override suspend fun load(automateContext: AutomateContext<S2Automate>, id: ID): ENTITY? {
        return projectionLoader.load(id)
    }
}

data class PersistTask<STATE, ID, ENTITY, EVENT>(val id: ID, val event: EVENT, val result: CompletableDeferred<ENTITY>) where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
EVENT: Evt,
EVENT: WithS2Id<ID>

class AutomateSourcingPersisterSnapChannel<STATE, ID, ENTITY, EVENT>(
    private val maxAttempts: Int = 5,
    private val delayMillis: Long = 500,
    private val persist: suspend (ID, EVENT) -> ENTITY
) : CoroutineScope where
STATE : S2State,
ENTITY : WithS2State<STATE>,
ENTITY : WithS2Id<ID>,
EVENT: Evt,
EVENT: WithS2Id<ID>  {
    // Replace these types with the actual types of S2InitCommand, ENTITY, and EVENT

    private val supervisorJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + supervisorJob

    private val persistChannel = Channel<PersistTask<STATE, ID, ENTITY, EVENT>>()

    init {
        launchPersistWorker()
    }

    private fun CoroutineScope.launchPersistWorker() = launch {
        for (task in persistChannel) {
            val result =retry{
                persist(task.id, task.event)
            }
            task.result.complete(result)
        }
    }

    suspend fun addToPersistQueue(id: ID, event: EVENT): ENTITY {
        val result = CompletableDeferred<ENTITY>()
        persistChannel.send(PersistTask(id, event, result))
        return result.await()
    }


    private suspend fun <T> retry(
        block: suspend () -> T
    ): T {
        var attempts = 0
        while (true) {
            try {
                return block()
            } catch (e: OptimisticLockingFailureException) {
                attempts++
                if (attempts >= maxAttempts) {
                    throw e
                }
                delay(delayMillis)
            }
        }
    }


    // Call this method to cancel all the child coroutines when the class is no longer needed
    fun cancelAllCoroutines() {
        supervisorJob.cancel()
    }
}
