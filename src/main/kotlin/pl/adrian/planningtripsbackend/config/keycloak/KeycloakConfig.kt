package pl.adrian.planningtripsbackend.config.keycloak

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.stereotype.Component

@Component
class KeycloakConfig() {

    companion object {
        var keycloak: Keycloak? = null


        fun getInstance(keycloakProperties: KeycloakProperties): Keycloak {
            if(keycloak == null) {
                keycloak = Keycloak.getInstance(keycloakProperties.serverUrl,
                    "master",
                    keycloakProperties.username,
                    keycloakProperties.password,
                    "admin-cli")
            }
            return keycloak!!
        }

        fun getInstanceByUserCredentials(username: String, password: String, keycloakProperties: KeycloakProperties) =
            KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.serverUrl)
                .realm(keycloakProperties.realm)
                .username(username)
                .password(password)
                .clientId(keycloakProperties.clientId)
                .clientSecret(keycloakProperties.clientSecret)
                .build()
    }
}