package pl.adrian.planningtripsbackend.trip.model.entity

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class Marker(

    @field:NotBlank
    var id: String = "",

    @field:NotBlank
    var title: String = "",

    @field:NotNull
    @field:Valid
    var position: Position? = null,
) {
}