package pl.adrian.planningtripsbackend.utils

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.time.Instant

class TokenUtils {

    companion object {

        private const val TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJka3JhajlSc3YyOVpua0had2VQcEtJNnB5SkladkxKbjBaUHQ1aGdyVVgwIn0.eyJleHAiOjE2MzQ1MjQ0OTIsImlhdCI6MTYzNDQ4ODQ5MiwianRpIjoiZTEwYWE5YjAtZTgwZi00YTYwLWIxNGItMGEwNjA1ZDFhMzhkIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDgwL2F1dGgvcmVhbG1zL1BsYW5uaW5nVHJpcHNSZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI0YjcwMmMxYS01NTM1LTQzYTMtOTNhZS04ZDQ3NTQ4ZDQ5N2EiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJsb2dpbi1hcHAiLCJzZXNzaW9uX3N0YXRlIjoiZTc5MDYzNDAtM2JjMS00ZGU5LTk4ZmQtNjg0MDJkMTA4ODdiIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJlbWFpbEBleGFtcGxlLmNvbSIsImVtYWlsIjoiZW1haWxAZXhhbXBsZS5jb20ifQ.hVtBqitZUWl6YmbArK2no1grz6WAbQrW58vWaWNB8AVpDjgtIO2dId3a3TmS0awA3Z-uPkGJ7-MHsgKBdnfP2QAQaUsXl2skBkXva-fIAl0Xhvuj8LU8bW8d-bLNPtRuBGkxD-GOGNETckWLbEgHdAUjLCWmAflKWKF7Mv7WkUbuzoIw9Ke7Wp77p2BEuKGc94qn99zbGASrknLQXTLtBODpiOGqspXI_-E1oKzfKaZpD0yOLLaOHdPYWKWybdazfifssV_gvMUhS3Otl5tkZybZ-e9lUSB4sfwJyTg91Mflh-nFdsVaHZ-BrhPt80qJeMOAEvnIkWCniVu43bcLGg";

        fun getJwtAuthenticationToken(): JwtAuthenticationToken {
            val userDetails = mapOf<String, Any>(
                "sub" to "4b702c1a-5535-43a3-93ae-8d47548d497a",
                "email" to "email@example.com"
            )
            val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
            val user = DefaultOAuth2User(authorities, userDetails, "sub")
            val headers = mapOf<String, Any>(
                "alg" to "RS256",
                "typ" to "JWT",
                "kid" to "dkraj9Rsv29ZnkHZwePpKI6pyJIZvLJn0ZPt5hgrUX0"
            )
            val jwt = Jwt(TOKEN, Instant.now(), Instant.now().plusSeconds(120), headers, userDetails
            )
            return JwtAuthenticationToken(jwt, authorities, "jwt")
        }
    }
}