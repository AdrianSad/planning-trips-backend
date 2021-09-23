package pl.adrian.planningtripsbackend.utils

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class SecurityUtils {

    companion object {
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
}