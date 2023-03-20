package s2.bdd.step.exceptions

import f2.spring.exception.NotFoundException
import s2.bdd.CucumberStepsDefinition
import s2.bdd.assertion.AssertionBdd
import s2.bdd.assertion.exceptions
import s2.bdd.data.TestContextKey
import s2.bdd.data.parser.safeExtract
import io.cucumber.java8.En
class NotFoundExceptionAssertionSteps: En, s2.bdd.CucumberStepsDefinition() {
    init {
        DataTableType(::notFoundParams)

        Then("The {string} should not be found") { objectName: String ->
            val lastUsedKey = context.testEntities<Any, Any>(objectName).lastUsedKey
            assert(lastUsedKey.toString())
        }

    }

    private fun assert(id: String) = step {
        AssertionBdd.exceptions(context)
            .assertThat(NotFoundException::class)
            .hasBeenThrown(1) { e -> id in e.message.orEmpty() }
    }

    private fun notFoundParams(entry: Map<String, String>) = NotFoundParams(
        identifier = entry.safeExtract("identifier")
    )

    private data class NotFoundParams(
        val identifier: TestContextKey
    )
}
