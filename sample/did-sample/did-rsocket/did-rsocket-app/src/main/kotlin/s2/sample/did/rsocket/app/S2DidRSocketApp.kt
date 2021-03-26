package s2.sample.did.rsocket.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@EntityScan("s2.sample.did")
@EnableReactiveMongoRepositories("s2.sample.did")
@SpringBootApplication(scanBasePackages = ["s2.sample.did"])
class S2DidRSocketApp

fun main(args: Array<String>) {
	runApplication<S2DidRSocketApp>(*args)
}

@Configuration
class Config {
	@Bean
	fun kotlinFunction(): java.util.function.Function<String, String> {
		return java.util.function.Function { it.toLowerCase() }
	}
}