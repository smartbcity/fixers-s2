package s2.spring.sourcing.data.mongodb

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.repository.support.ReactiveMongoRepositoryFactory
import org.springframework.data.repository.core.support.ReactiveRepositoryFactorySupport


@Configuration
open class MongodbReactiveMongoRepositoryConfig {
	@Bean
	open fun reactiveRepositoryFactorySupport(mongoOperations: ReactiveMongoOperations): ReactiveRepositoryFactorySupport {
		return ReactiveMongoRepositoryFactory(mongoOperations)
	}
}
