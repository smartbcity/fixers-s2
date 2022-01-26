package s2.sample.orderbook.storming.app.ssm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["s2.sample.orderbook.storming.app.ssm"])
class SubAutomateApp

fun main(args: Array<String>) {
	runApplication<SubAutomateApp>(*args)
}
