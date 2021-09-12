package s2.sample.did.http.app

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import org.springframework.web.cors.CorsConfiguration

@Configuration
class CorsConfig {
	@Bean
	fun corsWebFilter(): org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource {
		val corsConfig = CorsConfiguration()

		corsConfig.allowedOrigins = listOf("*")
		corsConfig.addAllowedMethod("GET")
		corsConfig.addAllowedMethod("PUT")
		corsConfig.addAllowedMethod("POST")
		corsConfig.addAllowedMethod("DELETE")
		corsConfig.addAllowedHeader("Access-Control-Allow-Headers")
		corsConfig.addAllowedHeader("Content-Type")
		corsConfig.addAllowedHeader("Authorization")
		corsConfig.addAllowedHeader("Content-Length")
		corsConfig.addAllowedHeader("X-Requested-With")

		val source = org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource()
		source.registerCorsConfiguration("/**", corsConfig)
		return source
	}
}
