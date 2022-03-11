package s2.sample.multiautomate.infiniteloop.entity

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import s2.sample.multiautomate.infiniteloop.InfiniteLoopId

@Repository
interface InfiniteLoopRepository : ReactiveMongoRepository<InfiniteLoopEntity, InfiniteLoopId>
