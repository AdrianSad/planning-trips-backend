package pl.adrian.planningtripsbackend.trip.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import pl.adrian.planningtripsbackend.trip.model.dto.CreateTripDto
import pl.adrian.planningtripsbackend.trip.model.dto.TripDto
import pl.adrian.planningtripsbackend.trip.model.entity.Trip

@Mapper(componentModel = "spring", uses = [])
interface TripMapper {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "createdDate", ignore = true),
        Mapping(target = "done", ignore = true),
        Mapping(target = "addedByUserId", ignore = true),
        Mapping(target = "estimatedTime", expression = "java(createTripDto.estimatedTime / 3600.0)"),
        Mapping(target = "estimatedLength", expression = "java(createTripDto.estimatedLength / 1000.0)")
    )
    fun toTrip(createTripDto: CreateTripDto): Trip

    fun toTripDto(trip: Trip): TripDto

    fun toTripsDto(trips: List<Trip>): List<TripDto>
}