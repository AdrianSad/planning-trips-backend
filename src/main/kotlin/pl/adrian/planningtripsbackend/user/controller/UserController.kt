package pl.adrian.planningtripsbackend.user.controller

import org.keycloak.representations.AccessTokenResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import pl.adrian.planningtripsbackend.user.model.dto.AuthenticateUserDto
import pl.adrian.planningtripsbackend.user.model.dto.CreateUserDto
import pl.adrian.planningtripsbackend.user.model.dto.UserDto
import pl.adrian.planningtripsbackend.user.service.UserService
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun createUser(@RequestBody @Valid createUserDto: CreateUserDto) =
        ResponseEntity<Unit>(userService.addUser(createUserDto), HttpStatus.CREATED)

    @PostMapping("/login")
    fun getJWT(@RequestBody @Valid authenticateUserDto: AuthenticateUserDto) =
        ResponseEntity<AccessTokenResponse?>(userService.getUserJWT(authenticateUserDto), HttpStatus.OK)

    @GetMapping
    fun getUserData(jwtAuthentication: JwtAuthenticationToken) =
        ResponseEntity<UserDto>(userService.getUserData(jwtAuthentication), HttpStatus.OK)
}