package s2.sample.did.app.entity

import s2.sample.did.domain.DidId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DidRepository : ReactiveMongoRepository<DidEntity, DidId>