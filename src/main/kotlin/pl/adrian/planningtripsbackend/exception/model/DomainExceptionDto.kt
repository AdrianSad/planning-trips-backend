package pl.adrian.planningtripsbackend.exception.model

import org.springframework.http.HttpStatus

data class DomainExceptionDto(var code: String = "",
                              var status: HttpStatus? = null,
                              var message: String = "")