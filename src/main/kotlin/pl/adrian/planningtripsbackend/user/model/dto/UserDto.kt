package pl.adrian.planningtripsbackend.user.model.dto

import pl.adrian.planningtripsbackend.trip.model.dto.TripDto
import java.util.*

data class UserDto(
    var id: String? = "",
    var username: String? = "",
    var email: String? = "",
    var weight: Double = 0.0,
    var age: Int = 1,
    var height: Double = 0.0,
    var trips: List<TripDto> = Collections.emptyList(),
    var statistics: UserStatistics = UserStatistics()
) {
}