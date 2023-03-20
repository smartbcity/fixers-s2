package s2.bdd.step.exceptions

import f2.spring.exception.ForbiddenAccessException
import s2.bdd.CucumberStepsDefinition
import s2.bdd.assertion.AssertionBdd
import s2.bdd.assertion.exceptions
import io.cucumber.java8.En

class ForbiddenExceptionAssertionSteps: En, s2.bdd.CucumberStepsDefinition() {
    init {
        Then("I should be forbidden to do so") {
            step {
                AssertionBdd.exceptions(context)
                    .assertThat(ForbiddenAccessException::class)
                    .hasBeenThrown(1)
            }
        }

        Then("I should not be forbidden to do so") {
            step {
                AssertionBdd.exceptions(context)
                    .assertThat(ForbiddenAccessException::class)
                    .hasNotBeenThrown()
            }
        }
    }
}
