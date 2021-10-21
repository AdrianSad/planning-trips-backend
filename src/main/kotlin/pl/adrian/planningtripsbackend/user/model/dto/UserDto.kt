package pl.adrian.planningtripsbackend.user.model.dto

import pl.adrian.planningtripsbackend.trip.model.entity.Trip
import java.util.*

data class UserDto(
    var id: String? = "",
    var username: String? = "",
    var email: String? = "",
    var trips: List<Trip> = Collections.emptyList(),
    var statistics: UserStatistics = UserStatistics()
) {
}