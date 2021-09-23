package pl.adrian.planningtripsbackend.config.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.util.stream.Collectors


internal class GrantedAuthoritiesExtractor :
    Converter<Jwt, Collection<GrantedAuthority>> {
    override fun convert(jwt: Jwt): Collection<GrantedAuthority> {
//        val authorities = jwt.claims["groups"] as Collection<String>?
//        return authorities!!.stream()
//            .map { role: String? ->
//                SimpleGrantedAuthority(
//                    role
//                )
//            }
//            .collect(Collectors.toList())
        return extractAuthorityFromClaims(jwt.claims)!!
    }

    fun extractAuthorityFromClaims(claims: Map<String, Any>): List<GrantedAuthority>? {
        return mapRolesToGrantedAuthorities(getRolesFromClaims(claims))
    }

    @Suppress("UNCHECKED_CAST")
    fun getRolesFromClaims(claims: Map<String, Any>): Collection<String> {
        return claims.getOrDefault("groups", claims.getOrDefault("roles", listOf<String>())) as Collection<String>
    }

    fun mapRolesToGrantedAuthorities(roles: Collection<String>): List<GrantedAuthority> {
        return roles
            .filter { it.startsWith("ROLE_") }
            .map { SimpleGrantedAuthority(it) }
    }
}