package s2.bdd

import f2.dsl.cqrs.Event
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import s2.bdd.data.TestContext

@Service
class TestApplicationEventListener(
    private val testContext: TestContext
) {

    @EventListener
    fun onApplicationEvent(event: Event) {
        testContext.events.add(event)
    }
}
