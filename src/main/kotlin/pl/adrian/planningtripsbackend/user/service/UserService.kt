package pl.adrian.planningtripsbackend.user.service

import io.vavr.control.Try
import org.keycloak.admin.client.resource.UsersResource
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import pl.adrian.planningtripsbackend.config.keycloak.KeycloakConfig
import pl.adrian.planningtripsbackend.config.keycloak.KeycloakProperties
import pl.adrian.planningtripsbackend.exception.model.BadRequestException
import pl.adrian.planningtripsbackend.trip.model.dto.TripDto
import pl.adrian.planningtripsbackend.trip.service.TripService
import pl.adrian.planningtripsbackend.user.mapper.UserMapper
import pl.adrian.planningtripsbackend.user.model.dto.*
import pl.adrian.planningtripsbackend.user.model.entity.Gender
import pl.adrian.planningtripsbackend.user.model.entity.User
import pl.adrian.planningtripsbackend.utils.CaloriesUtils
import java.time.Instant
import java.util.*

@Service
class UserService(
    private val userMapper: UserMapper,
    private val keycloakProperties: KeycloakProperties,
    private val tripService: TripService
) {

    fun addUser(createUserDto: CreateUserDto) {
        val user: User = userMapper.toUser(createUserDto)
        val usersResource: UsersResource =
            KeycloakConfig.getInstance(keycloakProperties).realm(keycloakProperties.realm).users()

        val search = usersResource.search(null, null, null, user.email, null, null)
        if (!CollectionUtils.isEmpty(search)) {
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

    private fun createPasswordCredentials(password: String): CredentialRepresentation {
        val passwordCredentials = CredentialRepresentation();
        passwordCredentials.isTemporary = false;
        passwordCredentials.type = CredentialRepresentation.PASSWORD;
        passwordCredentials.value = password;
        return passwordCredentials;
    }

    fun getUserJWT(authenticateUserDto: AuthenticateUserDto): AccessTokenResponse? {
        val atr = Try.of {
            KeycloakConfig.getInstanceByUserCredentials(
                authenticateUserDto.email!!,
                authenticateUserDto.password!!,
                keycloakProperties
            )
        }
            .onFailure { throw BadRequestException("INVALID_CREDENTIALS", it.message!!) }
            .get().tokenManager().accessToken
        return atr
    }

    fun getUserData(jwtAuthentication: JwtAuthenticationToken): UserDto {
        val jwt: Jwt = jwtAuthentication.principal as Jwt
        val usersResource: UsersResource =
            KeycloakConfig.getInstance(keycloakProperties).realm(keycloakProperties.realm).users()

        val foundUserResource = usersResource.get(jwt.subject.toString())
        val foundUser = foundUserResource.toRepresentation()
        return mapKeycloakUserToUserDTO(foundUser, jwtAuthentication)
    }

    private fun mapKeycloakUserToUserDTO(
        foundUser: UserRepresentation,
        jwtAuthentication: JwtAuthenticationToken
    ): UserDto {
        val trips = tripService.getUserTrips(jwtAuthentication)
        val weight = foundUser.attributes?.get("weight")?.get(0)?.toDouble()
        val height = foundUser.attributes?.get("height")?.get(0)?.toDouble()
        val age = foundUser.attributes?.get("age")?.get(0)?.toInt()
        val gender =
            if (foundUser.attributes?.get("gender")
                    ?.get(0) != null
            ) Gender.valueOf(foundUser.attributes["gender"]!![0]) else null
        return UserDto(
            id = foundUser.id,
            username = foundUser.username,
            email = foundUser.email,
            createdDate = Instant.ofEpochMilli(foundUser.createdTimestamp),
            weight = weight,
            height = height,
            age = age,
            trips = trips,
            gender = gender,
            statistics = calculateUserStatistics(trips, weight, height, age, gender)
        )
    }

    fun updateUserData(
        jwtAuthentication: JwtAuthenticationToken,
        updateUserDto: UpdateUserDto
    ): UserDto {
        val jwt: Jwt = jwtAuthentication.principal as Jwt
        val usersResource: UsersResource =
            KeycloakConfig.getInstance(keycloakProperties).realm(keycloakProperties.realm).users()

        val foundUserResource = usersResource.get(jwt.subject.toString())
        val foundUser = foundUserResource.toRepresentation()

        foundUser.attributes = mapOf(
            "weight" to listOf(updateUserDto.weight.toString()),
            "height" to listOf(updateUserDto.height.toString()),
            "age" to listOf(updateUserDto.age.toString()),
            "gender" to listOf(updateUserDto.gender.toString())
        ).toMutableMap()

        foundUserResource.update(foundUser)
        return mapKeycloakUserToUserDTO(foundUser, jwtAuthentication)
    }

    fun calculateUserStatistics(
        userTrips: List<TripDto>,
        weight: Double?,
        height: Double?,
        age: Int?,
        gender: Gender?
    ): UserStatistics {
        val filteredUserTrips = userTrips.filter { it.done }
        val kilometersTraveled = filteredUserTrips.sumOf { it.estimatedLength }
        val hoursSpent = filteredUserTrips.sumOf { it.estimatedTime }
        val caloriesBurned: Int?

        caloriesBurned = if (height != null && age != null && weight != null && gender != null) {
            filteredUserTrips.sumOf {
                CaloriesUtils.calculateEnergyExpenditure(
                    height,
                    age,
                    weight,
                    it.estimatedTime,
                    it.estimatedLength,
                    gender
                )
            }.toInt()
        } else {
            null
        }

        return UserStatistics(
            caloriesBurned = caloriesBurned,
            kilometersTraveled = kilometersTraveled,
            hoursSpent = hoursSpent
        )
    }
}