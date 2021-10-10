package pl.adrian.planningtripsbackend.exception.model

import org.springframework.http.HttpStatus

class BadRequestException(
    override val code: String,
    override val message: String) : DomainException(code, HttpStatus.BAD_REQUEST, message) {
}