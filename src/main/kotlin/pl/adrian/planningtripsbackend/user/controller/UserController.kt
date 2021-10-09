package pl.adrian.planningtripsbackend.user.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.adrian.planningtripsbackend.user.model.dto.AuthenticateUserDto
import pl.adrian.planningtripsbackend.user.model.dto.CreateUserDto
import pl.adrian.planningtripsbackend.user.service.UserService
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun createUser(@RequestBody @Valid createUserDto: CreateUserDto) = userService.addUser(createUserDto)

    @PostMapping("/login")
    fun getJWT(@RequestBody @Valid authenticateUserDto: AuthenticateUserDto) = userService.getUserJWT(authenticateUserDto)
}