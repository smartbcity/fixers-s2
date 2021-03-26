package s2.sample.did.http.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


@EntityScan("s2.sample")
@EnableReactiveMongoRepositories("s2.sample")
@SpringBootApplication(scanBasePackages = ["s2.sample"])
class S2DidHttpApp

fun main(args: Array<String>) {
	runApplication<S2DidHttpApp>(*args)
}