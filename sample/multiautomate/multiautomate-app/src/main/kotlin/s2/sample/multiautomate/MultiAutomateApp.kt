package s2.sample.multiautomate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


@EntityScan("s2.sample.multiautomate")
@EnableReactiveMongoRepositories("s2.sample.multiautomate")
@SpringBootApplication(scanBasePackages = ["s2.sample.multiautomate"])
class MultiAutomateApp

fun main(args: Array<String>) {
	runApplication<MultiAutomateApp>(*args)
}