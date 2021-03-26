package s2.sample.did.http.app

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter(config: UrlBasedCorsConfigurationSource) : CorsWebFilter(config)