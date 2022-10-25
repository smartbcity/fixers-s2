package s2.sample.did.http.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["s2.sample"])
class S2DidHttpApp

fun main(args: Array<String>) {
	runApplication<S2DidHttpApp>(*args)
}
