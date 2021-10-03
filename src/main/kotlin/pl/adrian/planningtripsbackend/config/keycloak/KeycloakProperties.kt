package pl.adrian.planningtripsbackend.config.keycloak

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("keycloak")
data class KeycloakProperties(
    var serverUrl: String = "",
    var realm: String = "",
    var clientId: String = "",
    var clientSecret: String = "",
    var username: String = "",
    var password: String = ""
) {
}