package pl.adrian.planningtripsbackend.exception.model

import org.springframework.http.HttpStatus

class NotFoundException(
    override val code: String,
    override val message: String) : DomainException(code, HttpStatus.NOT_FOUND, message) {
}