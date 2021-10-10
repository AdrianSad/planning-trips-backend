package pl.adrian.planningtripsbackend.exception.model

import org.springframework.http.HttpStatus

open class DomainException(
    open val code: String,
    val status: HttpStatus,
    override val message: String) : RuntimeException()