package pl.adrian.planningtripsbackend.user.model.dto

import pl.adrian.planningtripsbackend.trip.model.entity.Trip

data class UserDto(
    var id: String? = "",
    var username: String? = "",
    var email: String? = "",
    var trips: List<Trip>,
    var statistics: UserStatistics
) {
}