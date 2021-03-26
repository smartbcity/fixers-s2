package s2.sample.multiautomate.endable.entity

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import s2.sample.multiautomate.endable.EndableLoopId

@Repository
interface EndableLoopRepository : ReactiveMongoRepository<EndableLoopEntity, EndableLoopId>