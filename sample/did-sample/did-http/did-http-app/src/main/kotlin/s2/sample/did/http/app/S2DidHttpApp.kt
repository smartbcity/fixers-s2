package s2.sample.did.http.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

const val PACKAGE = "s2.sample"

@EntityScan(PACKAGE)
@EnableReactiveMongoRepositories(PACKAGE)
@SpringBootApplication(scanBasePackages = [PACKAGE])
class S2DidHttpApp

fun main(args: Array<String>) {
	runApplication<S2DidHttpApp>(*args)
}
