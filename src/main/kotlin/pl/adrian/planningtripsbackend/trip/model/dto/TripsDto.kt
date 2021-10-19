package pl.adrian.planningtripsbackend.trip.model.dto

import java.util.*

data class TripsDto(
    val trips: List<TripDto> = Collections.emptyList()
) {
}