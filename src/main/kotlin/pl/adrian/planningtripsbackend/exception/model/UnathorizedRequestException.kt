package pl.adrian.planningtripsbackend.exception.model

import org.springframework.http.HttpStatus

class UnathorizedRequestException(
    override val code: String,
    override val message: String) : DomainException(code, HttpStatus.UNAUTHORIZED, message) {
}