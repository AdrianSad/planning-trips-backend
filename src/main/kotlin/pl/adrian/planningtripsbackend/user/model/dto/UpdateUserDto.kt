package pl.adrian.planningtripsbackend.user.model.dto

import pl.adrian.planningtripsbackend.user.model.entity.Gender
import javax.validation.constraints.NotNull

data class UpdateUserDto(

    @field:NotNull
    val weight: Double? = null,

    @field:NotNull
    val age: Int? = null,

    @field:NotNull
    val height: Double? = null,

    @field:NotNull
    val gender: Gender? = null,
) {
}