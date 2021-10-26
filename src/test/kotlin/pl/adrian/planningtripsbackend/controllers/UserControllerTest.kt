package pl.adrian.planningtripsbackend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.RandomStringUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.TestSecurityContextHolder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.adrian.planningtripsbackend.PlanningTripsBackendApplication
import pl.adrian.planningtripsbackend.config.TestSecurityConfiguration
import pl.adrian.planningtripsbackend.user.mapper.UserMapper
import pl.adrian.planningtripsbackend.user.model.dto.AuthenticateUserDto
import pl.adrian.planningtripsbackend.user.model.dto.CreateUserDto
import pl.adrian.planningtripsbackend.user.model.dto.UpdateUserDto
import pl.adrian.planningtripsbackend.user.model.entity.User
import pl.adrian.planningtripsbackend.config.TokenConfig
import pl.adrian.planningtripsbackend.user.model.entity.Gender
import kotlin.jvm.Throws

@AutoConfigureMockMvc
@WithMockUser(authorities = ["ROLE_USER"])
@SpringBootTest(classes = [PlanningTripsBackendApplication::class, TestSecurityConfiguration::class])
class UserControllerTest {

    @Autowired
    private lateinit var restUserMockMvc: MockMvc

    @Autowired
    private lateinit var userMapper: UserMapper

    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = createEntity()
    }

    @Test
    @Throws(Exception::class)
    fun createUser() {

        val createUserDto = CreateUserDto(
            DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5),
            DEFAULT_PASSWORD,
            RandomStringUtils.randomAlphabetic(5) + DEFAULT_EMAIL
        )
        val writeValueAsBytes = ObjectMapper().writeValueAsBytes(createUserDto)

        restUserMockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsBytes)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    @Throws(Exception::class)
    fun createUserWhichAlreadyExists() {

        val createUserDto = CreateUserDto(
            DEFAULT_USERNAME,
            DEFAULT_PASSWORD,
            DEFAULT_EMAIL
        )

        val writeValueAsBytes = ObjectMapper().writeValueAsBytes(createUserDto)

        restUserMockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsBytes)
        )

        restUserMockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsBytes)
        )
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.code").value("USER_ALREADY_EXISTS"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.status").value("BAD_REQUEST"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("User with this email already exists"))
    }

    @Test
    @Throws(Exception::class)
    fun getUser() {

        val authenticateUserDto = AuthenticateUserDto(DEFAULT_EMAIL, DEFAULT_PASSWORD)
        val writeValueAsBytes = ObjectMapper().writeValueAsBytes(authenticateUserDto)

        restUserMockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsBytes)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.access_token").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.refresh_token").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.expires_in").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.refresh_expires_in").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.token_type").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.scope").exists())
    }

    @Test
    @Throws(Exception::class)
    fun getUserData() {

        TestSecurityContextHolder.getContext().authentication = TokenConfig.getJwtAuthenticationToken()

        restUserMockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/user")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.email").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.username").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.trips").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statistics").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.createdDate").exists())
    }

    @Test
    @Throws(Exception::class)
    fun updateUser() {
        TestSecurityContextHolder.getContext().authentication = TokenConfig.getJwtAuthenticationToken()

        val UPDATE_WEIGHT = 1.5
        val UPDATE_HEIGHT = 1.6
        val UPDATE_AGE = 2
        val UPDATE_GENDER = Gender.FEMALE

        val authenticateUserDto =
            UpdateUserDto(weight = UPDATE_WEIGHT, height = UPDATE_HEIGHT, age = UPDATE_AGE, gender = UPDATE_GENDER)
        val writeValueAsBytes = ObjectMapper().writeValueAsBytes(authenticateUserDto)

        restUserMockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsBytes)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.weight").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.weight").value(UPDATE_WEIGHT))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.height").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.height").value(UPDATE_HEIGHT))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.age").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.age").value(UPDATE_AGE))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.gender").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.gender").value(UPDATE_GENDER.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.email").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.username").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.trips").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statistics").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("\$.createdDate").exists())
    }

    @Test
    @Throws(Exception::class)
    fun updateUserWithMissingData() {
        TestSecurityContextHolder.getContext().authentication = TokenConfig.getJwtAuthenticationToken()

        val UPDATE_WEIGHT = 1.5
        val UPDATE_HEIGHT = 1.6

        val authenticateUserDto = UpdateUserDto(weight = UPDATE_WEIGHT, height = UPDATE_HEIGHT)
        val writeValueAsBytes = ObjectMapper().writeValueAsBytes(authenticateUserDto)

        restUserMockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValueAsBytes)
        )
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    @Throws(Exception::class)
    fun testUserEquals() {
        val user1 = User(id = "id1")
        val user2 = User(id = user1.id)
        assertThat(user1).isEqualTo(user2)
        user2.id = "id2"
        assertThat(user1).isNotEqualTo(user2)
        user1.id = null
        assertThat(user1).isNotEqualTo(user2)
    }

    @Test
    fun testCreateUserToUser() {
        val createUserDto = CreateUserDto(
            username = DEFAULT_USERNAME,
            password = DEFAULT_PASSWORD,
            email = DEFAULT_EMAIL,
        )

        val user = userMapper.toUser(createUserDto)
        assertNotNull(user)
        assertThat(user.username).isEqualTo(DEFAULT_USERNAME)
        assertThat(user.email).isEqualTo(DEFAULT_EMAIL)
    }

    companion object {

        private const val DEFAULT_ID = "id1"

        private const val DEFAULT_USERNAME = "username"

        private const val DEFAULT_EMAIL = "email@example.com"

        private const val DEFAULT_PASSWORD = "password"

        @JvmStatic
        fun createEntity(): User {
            return User(
                id = DEFAULT_ID,
                username = DEFAULT_USERNAME + RandomStringUtils.randomAlphabetic(5),
                email = RandomStringUtils.randomAlphabetic(5) + DEFAULT_EMAIL,
            )
        }
    }
}