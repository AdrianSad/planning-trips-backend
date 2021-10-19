package pl.adrian.planningtripsbackend.trip.service

import com.nimbusds.oauth2.sdk.auth.JWTAuthentication
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service
import pl.adrian.planningtripsbackend.exception.model.NotFoundException
import pl.adrian.planningtripsbackend.trip.mapper.TripMapper
import pl.adrian.planningtripsbackend.trip.model.dto.CreateTripDto
import pl.adrian.planningtripsbackend.trip.model.dto.TripDto
import pl.adrian.planningtripsbackend.trip.model.dto.TripsDto
import pl.adrian.planningtripsbackend.trip.repository.TripRepository
import pl.adrian.planningtripsbackend.utils.ValidationUtils

@Service
class TripService(private val tripRepository: TripRepository,
                  private val tripMapper: TripMapper) {

    fun createTrip(createTripDto: CreateTripDto, jwtAuthentication: JwtAuthenticationToken): TripDto {

        val trip = tripMapper.toTrip(createTripDto);

        val jwt: Jwt = jwtAuthentication.principal as Jwt
        trip.addedByUserId = jwt.subject.toString()

        val createdTrip = tripRepository.save(trip)
        return tripMapper.toTripDto(createdTrip)
    }

    fun getUserTrips(jwtAuthentication: JwtAuthenticationToken): TripsDto {
        val jwt: Jwt = jwtAuthentication.principal as Jwt

        val userTrips = tripRepository.findAllByAddedByUserId(jwt.subject.toString())
        val userTripsDto = tripMapper.toTripsDto(userTrips)
        return TripsDto(userTripsDto)
    }

    fun updateTripDoneFlag(jwtAuthentication: JwtAuthenticationToken, tripId: String, done: Boolean) {
        val trip = tripRepository.findById(tripId).orElseThrow { NotFoundException("TRIP_NOT_FOUND", "Trip not found") }

        ValidationUtils.isUserTripOwner(trip, jwtAuthentication)

        trip.done = done
        tripRepository.save(trip)
    }

    fun deleteTrip(jwtAuthentication: JwtAuthenticationToken, tripId: String) {
        val trip = tripRepository.findById(tripId).orElseThrow { NotFoundException("TRIP_NOT_FOUND", "Trip not found") }

        ValidationUtils.isUserTripOwner(trip, jwtAuthentication)
        tripRepository.delete(trip)
    }
}