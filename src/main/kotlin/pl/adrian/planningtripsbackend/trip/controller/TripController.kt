package pl.adrian.planningtripsbackend.trip.controller

import com.nimbusds.oauth2.sdk.auth.JWTAuthentication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.adrian.planningtripsbackend.trip.model.dto.CreateTripDto
import pl.adrian.planningtripsbackend.trip.model.dto.TripDto
import pl.adrian.planningtripsbackend.trip.service.TripService
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/trip")
class TripController(private val tripService: TripService) {

    @PostMapping
    fun createTrip(@Valid @RequestBody createTripDto: CreateTripDto, jwtAuthentication: JwtAuthenticationToken) =
        ResponseEntity<TripDto>(tripService.createTrip(createTripDto, jwtAuthentication), HttpStatus.CREATED)
}