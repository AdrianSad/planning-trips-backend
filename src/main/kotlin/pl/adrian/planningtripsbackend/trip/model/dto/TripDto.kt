package pl.adrian.planningtripsbackend.trip.model.dto

import org.springframework.data.annotation.CreatedDate
import pl.adrian.planningtripsbackend.trip.model.entity.Marker
import pl.adrian.planningtripsbackend.trip.model.entity.TravelMode
import java.time.Instant
import java.util.*

data class TripDto(
    var id: String? = "",
    var createdDate: Instant? = null,
    var image: String = "",
    var route : String = "",
    var addedByUserId: String = "",
    var estimatedTime: Double = 0.0,
    var estimatedLength: Double = 0.0,
    var travelMode: TravelMode = TravelMode.WALKING,
    var markers: List<Marker> = Collections.emptyList(),
    var done: Boolean = false
) {
}