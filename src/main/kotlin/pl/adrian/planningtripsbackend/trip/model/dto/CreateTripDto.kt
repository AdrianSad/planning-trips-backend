package pl.adrian.planningtripsbackend.trip.model.dto

import pl.adrian.planningtripsbackend.trip.model.entity.Marker
import pl.adrian.planningtripsbackend.trip.model.entity.TravelMode
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import kotlin.math.min

data class CreateTripDto(

    @field:NotBlank
    val route : String = "",

    val image: String = "",

    @field:NotNull
    val estimatedTime: Double = 0.0,

    @field:NotNull
    val estimatedLength: Double = 0.0,

    @field:NotNull
    val travelMode: TravelMode = TravelMode.WALKING,

    @field:Size(min = 2)
    @field:Valid
    val markers: List<Marker> = Collections.emptyList()
) {
}