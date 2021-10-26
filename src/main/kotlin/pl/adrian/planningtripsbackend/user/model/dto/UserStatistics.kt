package pl.adrian.planningtripsbackend.user.model.dto

data class UserStatistics(
    var caloriesBurned: Int? = null,
    var kilometersTraveled: Double? = null,
    var hoursSpent: Double? = null
) {
}