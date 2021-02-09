package city.smartb.s2.spring.aggregate.snap

import city.smartb.s2.dsl.aggregate.entity.WithS2IdAndStatus
import city.smartb.s2.dsl.aggregate.event.StateSnapAppEvent
import city.smartb.s2.dsl.automate.S2State
import city.smartb.s2.spring.aggregate.snap.entity.SnapEntityRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

//@Configuration
open class SnapFetcherCommand<STATE: S2State, ID, ENTITY: WithS2IdAndStatus<ID, STATE>> {


}