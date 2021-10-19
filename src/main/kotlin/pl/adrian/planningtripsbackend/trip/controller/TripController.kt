package pl.adrian.planningtripsbackend.trip.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import pl.adrian.planningtripsbackend.trip.model.dto.CreateTripDto
import pl.adrian.planningtripsbackend.trip.model.dto.TripDto
import pl.adrian.planningtripsbackend.trip.model.dto.TripsDto
import pl.adrian.planningtripsbackend.trip.service.TripService
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/trip")
class TripController(private val tripService: TripService) {

    @PostMapping
    fun createTrip(@Valid @RequestBody createTripDto: CreateTripDto, jwtAuthentication: JwtAuthenticationToken) =
        ResponseEntity<TripDto>(tripService.createTrip(createTripDto, jwtAuthentication), HttpStatus.CREATED)

    @GetMapping
    fun getTrips(jwtAuthentication: JwtAuthenticationToken) =
        ResponseEntity<TripsDto>(tripService.getUserTrips(jwtAuthentication), HttpStatus.OK)
}