package pl.adrian.planningtripsbackend.user.model.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateUserDto(

    @field:NotBlank
    @field:Size(min = 5, max = 100)
    var username: String? = "",

    @field:NotBlank
    @field:Size(min = 5, max = 100)
    var password: String? = "",

    @field:Email
    var email: String? = ""
) {}