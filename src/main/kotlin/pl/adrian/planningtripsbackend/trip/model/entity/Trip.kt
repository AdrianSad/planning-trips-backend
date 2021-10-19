package pl.adrian.planningtripsbackend.trip.model.entity

import nonapi.io.github.classgraph.json.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*

@Document
data class Trip(

    @Id
    var id: String? = "",

    @CreatedDate
    var createdDate: Instant? = null,

    var image: String = "",
    var route : String = "",
    var addedByUserId: String = "",
    var estimatedTime: Double = 0.0,
    var estimatedLength: Double = 0.0,
    var travelMode: TravelMode = TravelMode.WALKING,
    var markers: List<Marker> = Collections.emptyList()
    ) {
}