package pl.adrian.planningtripsbackend.utils

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.time.Instant

class TokenUtils {

    companion object {

        private const val TOKEN =
            "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJkSFBzMUxzOTh0Z25OTDBmMjFMUE9lNWhnZEw3aU40bWU4ZHJ3SVFPS2dFIn0.eyJleHAiOjE2MzQ4NDE2MDIsImlhdCI6MTYzNDgwNTYwMiwianRpIjoiNzAyNTY0YmYtMjQ2NC00YjQzLTg0ZDEtMWYxZGRhOTQzY2FhIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDgwL2F1dGgvcmVhbG1zL1BsYW5uaW5nVHJpcHNSZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJiODMzOGQyOS1hODI2LTQ3ZGMtOWY2Yy01YWRmMzIzNGVkN2YiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJsb2dpbi1hcHAiLCJzZXNzaW9uX3N0YXRlIjoiYWE4NWY3ZWYtMDRkOS00MGRmLWE0NjEtMTcwYmU5ZDA3NGE2IiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJlbWFpbEBleGFtcGxlLmNvbSIsImVtYWlsIjoiZW1haWxAZXhhbXBsZS5jb20ifQ.JYaIC8OnV2ECnK4U0vcQk3pRmvZtrPaDGrEFYZ-7VDg2L3lztHLgnE01EMDGFXcIen8cSgqjROZs5uNOj-n4aiUdTUgFxTqS8OePEsNOZRrfoBFDjM_7s0O232mzIVwoEINtM1YiZGMZhqGXaPqb657zpbwVRFA0ggXrG9aNqfHrGHwm2AbdcillqQNbrNzJiXiOjus4mc4jMH4TZ7QuC3LdnAPLHXcxc0AwQn7FjeczX88tEB8z9DJY1O2YcjC-SfbPlB31tm8YFOom8vnGBA-qWedRS-5BnkiI-g0Ik5P6M-37YucaidXcG9ihhGkXwnNA4EMvtdCqct5hNqygKA";

        fun getJwtAuthenticationToken(): JwtAuthenticationToken {
            val userDetails = mapOf<String, Any>(
                "sub" to "b8338d29-a826-47dc-9f6c-5adf3234ed7f",
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