package pl.adrian.planningtripsbackend.user.service

import io.vavr.control.Try
import org.keycloak.admin.client.resource.UsersResource
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import pl.adrian.planningtripsbackend.config.keycloak.KeycloakConfig
import pl.adrian.planningtripsbackend.config.keycloak.KeycloakProperties
import pl.adrian.planningtripsbackend.exception.model.BadRequestException
import pl.adrian.planningtripsbackend.user.mapper.UserMapper
import pl.adrian.planningtripsbackend.user.model.dto.AuthenticateUserDto
import pl.adrian.planningtripsbackend.user.model.dto.CreateUserDto
import pl.adrian.planningtripsbackend.user.model.dto.UserDto
import pl.adrian.planningtripsbackend.user.model.entity.User
import java.util.*

@Service
class UserService(private val userMapper: UserMapper,
                  private val keycloakProperties: KeycloakProperties
) {

    fun addUser(createUserDto: CreateUserDto) {
        val user: User = userMapper.toUser(createUserDto)
        val usersResource: UsersResource = KeycloakConfig.getInstance(keycloakProperties).realm(keycloakProperties.realm).users()

        val search = usersResource.search(null, null, null, user.email, null, null)
        if(!CollectionUtils.isEmpty(search)) {
            throw BadRequestException("USER_ALREADY_EXISTS", "User with this email already exists")
        }

        val credentialRepresentation: CredentialRepresentation = createPasswordCredentials(createUserDto.password!!)
        val kcUser = UserRepresentation()
        kcUser.credentials = Collections.singletonList(credentialRepresentation)
        kcUser.username = user.username
        kcUser.email = user.email
        kcUser.isEnabled = true
        kcUser.isEmailVerified = false
        usersResource.create(kcUser)
    }

    private fun createPasswordCredentials(password: String) : CredentialRepresentation
    {
        val passwordCredentials = CredentialRepresentation();
        passwordCredentials.isTemporary = false;
        passwordCredentials.type = CredentialRepresentation.PASSWORD;
        passwordCredentials.value = password;
        return passwordCredentials;
    }

    fun getUserJWT(authenticateUserDto: AuthenticateUserDto): AccessTokenResponse? {
        val atr = Try.of { KeycloakConfig.getInstanceByUserCredentials(
            authenticateUserDto.email!!,
            authenticateUserDto.password!!,
            keycloakProperties
        ) }
            .onFailure { throw BadRequestException("INVALID_CREDENTIALS", it.message!!) }
            .get().tokenManager().accessToken
        return atr
    }

    fun getUserData(jwtAuthentication: JwtAuthenticationToken): UserDto {
        TODO("Not yet implemented");
    }
}