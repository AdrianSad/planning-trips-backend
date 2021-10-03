package pl.adrian.planningtripsbackend.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@EnableWebFluxSecurity
@Configuration
class SecurityConfig: WebFluxConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods("PUT")
            .allowedMethods("DELETE")
            .allowedMethods("PATCH")
            .maxAge(3600);    }

    @Bean
    fun securitygWebFilterChain(
        http: ServerHttpSecurity
    ): SecurityWebFilterChain? {
        http.headers().frameOptions().disable()
        return http
            .csrf().disable()
            .cors().configurationSource{getCorsConfiguration()}
            .and()
            .authorizeExchange()
            .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .pathMatchers(HttpMethod.POST, "/api/v1/user/register").permitAll()
            .pathMatchers(HttpMethod.POST, "/api/v1/user/login").permitAll()
            .anyExchange().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt().and().and().build()
    }

    private fun getCorsConfiguration(): CorsConfiguration {
        val corsConfiguration = CorsConfiguration().applyPermitDefaultValues()
        corsConfiguration.addAllowedMethod(HttpMethod.PATCH)
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE)
        return corsConfiguration
    }
}