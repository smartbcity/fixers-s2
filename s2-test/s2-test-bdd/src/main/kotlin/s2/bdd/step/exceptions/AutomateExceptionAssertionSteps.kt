package s2.bdd.step.exceptions

import s2.bdd.CucumberStepsDefinition
import s2.bdd.assertion.AssertionBdd
import s2.bdd.assertion.exceptions
import io.cucumber.java8.En
import s2.automate.core.error.AutomateException

class AutomateExceptionAssertionSteps: En, s2.bdd.CucumberStepsDefinition() {
    init {
        DataTableType(::exceptionAssertionParams)

        Then("The automate transition should be invalid") {
            step {
                assert(exceptionAssertionParams(null))
            }
        }
        Then("The automate transition should be invalid:") { params: AutomateExceptionAssertionParams ->
            step {
                assert(params)
            }
        }
    }

    private fun assert(params: AutomateExceptionAssertionParams) {
        AssertionBdd.exceptions(context)
            .assertThat(AutomateException::class)
            .hasBeenThrown(params.times) { e ->
                e.errors.any { error ->
                    error.type == "ERROR_INVALID_TRANSITION"
                            && (params.from == null || error.payload["from"] == params.from)
                }
            }
    }

    private fun exceptionAssertionParams(entry: Map<String, String>?) = AutomateExceptionAssertionParams(
        times = entry?.get("times")?.toInt() ?: 1,
        from = entry?.get("from")
    )

    private data class AutomateExceptionAssertionParams(
        val times: Int,
        val from: String?
    )
}
