package s2.bdd

import org.springframework.beans.factory.annotation.Autowired
import s2.bdd.data.TestContext

open class S2CucumberStepsDefinition: CucumberStepsDefinition() {
    @Autowired
    override lateinit var context: TestContext
}
