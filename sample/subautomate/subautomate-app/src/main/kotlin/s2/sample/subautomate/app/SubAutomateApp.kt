package s2.sample.subautomate.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@EntityScan(basePackages=["s2.sample.subautomate"])
@EnableReactiveMongoRepositories(basePackages=["s2.sample.subautomate"])
@SpringBootApplication(scanBasePackages = ["s2.sample.subautomate"])
class SubAutomateApp

fun main(args: Array<String>) {
	runApplication<SubAutomateApp>(*args)
}
