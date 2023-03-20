package s2.bdd.assertion

import s2.bdd.data.BddContext
import s2.bdd.data.TestContext
import org.assertj.core.api.Assertions
import kotlin.reflect.KClass

fun AssertionBdd.exceptions(testContext: BddContext) = AssertionExceptions(testContext)

class AssertionExceptions(
    private val context: BddContext
) {
    fun <E: Exception> assertThat(exceptionClass: KClass<E>) = ExceptionAssert(
        exceptions = context.errors().filterIsInstance(exceptionClass)
    )

    inner class ExceptionAssert<E: Exception>(
        private val exceptions: Collection<E>
    ) {
        fun hasNotBeenThrown(matcher: (E) -> Boolean = { true }) {
            hasBeenThrown(0, matcher)
        }

        fun hasBeenThrown(times: Int, matcher: (E) -> Boolean = { true }) {
            matching(matcher).hasSize(times)
        }

        fun hasBeenThrownAtLeast(times: Int, matcher: (E) -> Boolean = { true }) {
            matching(matcher).hasSizeGreaterThanOrEqualTo(times)
        }

        fun hasBeenThrownAtMost(times: Int, matcher: (E) -> Boolean = { true }) {
            matching(matcher).hasSizeLessThanOrEqualTo(times)
        }

        fun matching(matcher: (E) -> Boolean) = Assertions.assertThat(exceptions.filter(matcher))
    }
}
