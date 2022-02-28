package s2.sample.orderbook.sourcing.app.ssm.config

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import s2.sample.orderbook.sourcing.app.ssm.SubAutomateSsmApp

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [SubAutomateSsmApp::class])
abstract class SpringTestBase
