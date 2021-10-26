package pl.adrian.planningtripsbackend.config

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.time.Instant

class TokenConfig {

    companion object {

        private const val TOKEN =
            "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIyaDRUN1BaenpiNGRHaHl4cFEwaW81dlpLTndiamZ5QUdQZUQzbDhzRWVnIn0.eyJleHAiOjE2MzUzMTI0OTYsImlhdCI6MTYzNTI3NjQ5NiwianRpIjoiMjVlNzdiN2MtMDBhOS00MDg3LWFjYTYtY2IxZmQwMmMzNTUyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDgwL2F1dGgvcmVhbG1zL1BsYW5uaW5nVHJpcHNSZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmNGQ1OTgzOC00OGQzLTQ0ODAtYTllYS00MTMzMmJlNzhkMWMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJsb2dpbi1hcHAiLCJzZXNzaW9uX3N0YXRlIjoiMjU3NmQxNzktMTYyYi00Y2Y5LWFmM2YtYmM4MGJhOTNlMzBjIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ1c2VybmFtZSIsImVtYWlsIjoiZW1haWxAZXhhbXBsZS5jb20ifQ.PBsUXuh4xQA3NPyn3_whUPup8SCgfiFYOtHWHf9LpsQOIOyw2oqj3t48yR4TNRkZEq-eWwaOP3cp1s9t1pFKU1wAFYZWU2CaZETLt7hNlX-e9U3gG3kDC2lGgsVHC8BuMkOUcUjG_Ra9qFszsOExwEPMV4hJUpOYiCynMJy_OptmBuu7LffsXwqGoTsGMIUz3PvrI4fpWRsBWPnpLciYKXYyxOjoKlkKyqnlL0nLzlw7DggOu_de8YjWkC4OymYvoAaPpRrlw8feCzsnxLb-JIdQv-swbZcTYdInJK9SUIsN5pKfUeGCAe2WyYNIEkHstmcSAmqag931T7jouWnzCw";

        fun getJwtAuthenticationToken(): JwtAuthenticationToken {
            val userDetails = mapOf<String, Any>(
                "sub" to "f4d59838-48d3-4480-a9ea-41332be78d1c",
                "email" to "email@example.com"
            )
            val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
            val user = DefaultOAuth2User(authorities, userDetails, "sub")
            val headers = mapOf<String, Any>(
                "alg" to "RS256",
                "typ" to "JWT",
                "kid" to "dkraj9Rsv29ZnkHZwePpKI6pyJIZvLJn0ZPt5hgrUX0"
            )
            val jwt = Jwt(
                TOKEN, Instant.now(), Instant.now().plusSeconds(120), headers, userDetails
            )
            return JwtAuthenticationToken(jwt, authorities, "jwt")
        }
    }
}