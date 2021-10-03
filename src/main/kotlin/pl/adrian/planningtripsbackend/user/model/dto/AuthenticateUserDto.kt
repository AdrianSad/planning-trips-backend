package pl.adrian.planningtripsbackend.user.model.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class AuthenticateUserDto(

    @field:Email
    var email: String? = "",

    @field:NotBlank
    @field:Size(min = 5, max = 100)
    var password: String? = "",
) {}
