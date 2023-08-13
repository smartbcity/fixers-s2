package s2.spring.automate.sourcing

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.dao.OptimisticLockingFailureException

data class PersistTask<ID, ENTITY, EVENT>(
    val id: ID,
    val event: EVENT,
    val result: CompletableDeferred<Pair<ENTITY?, Throwable?>>,
    val persist: suspend (ID, EVENT) -> ENTITY
)

class AutomateSourcingPersisterSnapChannel(
    private val maxAttempts: Int = 5,
    private val delayMillis: Long = 1000,
) : CoroutineScope {

//    init {
//        println("//////////////////////////////////////////////////////////////////////")
//        println("//////////////////////////////////////////////////////////////////////")
//        println("AutomateSourcingPersisterSnapChannel ${this}")
//        println("//////////////////////////////////////////////////////////////////////")
//        println("//////////////////////////////////////////////////////////////////////")
//    }
//    // Replace these types with the actual types of S2InitCommand, ENTITY, and EVENT

    private val supervisorJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + supervisorJob

    private val persistChannel = Channel<PersistTask<Any, Any, Any>>()

    init {
        launchPersistWorker()
    }

    private fun CoroutineScope.launchPersistWorker() = launch {
        for (task in persistChannel) {
            val result = retry {
                task.persist(task.id, task.event)
            }
            task.result.complete(result)
        }
    }

    suspend fun <ID, ENTITY, EVENT> addToPersistQueue(
        id: ID, event: EVENT, persist: suspend (ID, EVENT) -> ENTITY
    ): ENTITY {
        val resultDeferred = CompletableDeferred<Pair<ENTITY?, Throwable?>>()
        val task = PersistTask(id, event, resultDeferred, persist) as PersistTask<Any, Any, Any>
        persistChannel.send(task)
        val (entity, exception) = resultDeferred.await()
        if(exception != null) throw exception
        return entity!!
    }


    private suspend fun <T> retry(
        block: suspend () -> T
    ): Pair<T?, Throwable?> {
        var attempts = 0
        while (true) {
            try {
                return block() to null
            } catch (e: Throwable) {
                return (null to e)
            }
            catch (e: OptimisticLockingFailureException) {
                attempts++
                if (attempts >= maxAttempts) {
                    return (null to e)
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
