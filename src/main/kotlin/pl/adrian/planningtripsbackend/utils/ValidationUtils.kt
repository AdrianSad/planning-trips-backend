package pl.adrian.planningtripsbackend.utils

import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import pl.adrian.planningtripsbackend.exception.model.UnathorizedRequestException
import pl.adrian.planningtripsbackend.trip.model.entity.Trip

class ValidationUtils {

    companion object {
        fun isUserTripOwner(trip: Trip, userAuth: JwtAuthenticationToken) {
            val jwt: Jwt = userAuth.principal as Jwt

            if(trip.addedByUserId != jwt.subject.toString()) {
              throw UnathorizedRequestException("NOT_USER_TRIP", "User has no permission in this trip")
            }
        }
    }
}