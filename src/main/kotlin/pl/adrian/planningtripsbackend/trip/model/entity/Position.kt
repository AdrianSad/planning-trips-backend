package pl.adrian.planningtripsbackend.trip.model.entity

import javax.validation.constraints.NotNull

data class Position(

    @field:NotNull
    var latitude: Double,

    @field:NotNull
    var longitude: Double,
) {
}