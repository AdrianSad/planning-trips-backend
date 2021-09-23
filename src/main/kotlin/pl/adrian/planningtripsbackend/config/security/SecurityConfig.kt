package pl.adrian.planningtripsbackend.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority
import org.springframework.security.oauth2.jwt.*
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter
import org.springframework.security.web.server.SecurityWebFilterChain
import pl.adrian.planningtripsbackend.utils.SecurityUtils.Companion.extractAuthorityFromClaims
import reactor.core.publisher.Mono

@EnableWebFluxSecurity
@Configuration
class SecurityConfig(){

    @Bean
    fun securitygWebFilterChain(
        http: ServerHttpSecurity
    ): SecurityWebFilterChain? {
        return http
            .csrf().disable()
            .authorizeExchange()
            .anyExchange().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt().and().and().build()
    }

    fun grantedAuthoritiesExtractor(): Converter<Jwt?, Mono<AbstractAuthenticationToken?>?>? {
        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(GrantedAuthoritiesExtractor())
        return ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter)
    }

//    @Bean
//    fun userAuthoritiesMapper() =
//        GrantedAuthoritiesMapper { authorities ->
//            val mappedAuthorities = mutableSetOf<GrantedAuthority>()
//
//            authorities.forEach {
//                // Check for OidcUserAuthority because Spring Security 5.2 returns
//                // each scope as a GrantedAuthority, which we don't care about.
//                if (it is OidcUserAuthority) {
//                    extractAuthorityFromClaims(it.userInfo.claims)?.let(mappedAuthorities::addAll)
//                }
//            }
//            mappedAuthorities
//        }

//    @Bean
//    fun jwtDecoder(): ReactiveJwtDecoder  {
//        val jwtDecoder = ReactiveJwtDecoders.fromOidcIssuerLocation(issuerUri) as NimbusReactiveJwtDecoder
//
//        val audienceValidator: OAuth2TokenValidator<Jwt> = AudienceValidator(listOf("account", "asd"))
//        val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer(issuerUri)
//        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)
//
//        jwtDecoder.setJwtValidator(withAudience)
//        return jwtDecoder
//    }

}