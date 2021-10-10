package pl.adrian.planningtripsbackend.exception.handler

import pl.adrian.planningtripsbackend.exception.model.DomainException
import pl.adrian.planningtripsbackend.exception.model.DomainExceptionDto
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.Instant
import java.util.stream.Collectors
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import kotlin.collections.LinkedHashMap

@RestControllerAdvice
class GeneralExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any?> {
        val validationErrors: MutableMap<Any, Any> = mutableMapOf()
        ex.bindingResult.fieldErrors.forEach { fieldError -> validationErrors[fieldError.field] = fieldError.defaultMessage!! }
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request, validationErrors)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(exception: ConstraintViolationException,
                                  request: WebRequest): ResponseEntity<Any?>? {
        val validationErrors: MutableMap<Any, Any> = exception
                .constraintViolations
                .stream()
                .collect(Collectors.toMap({ obj: ConstraintViolation<*> -> obj.propertyPath.toString() },
                        { obj: ConstraintViolation<*> -> obj.message.toString() }))
        return getExceptionResponseEntity(HttpStatus.BAD_REQUEST, request, validationErrors)
    }

    private fun getExceptionResponseEntity(status: HttpStatus,
                                           request: WebRequest,
                                           errors: MutableMap<Any, Any>): ResponseEntity<Any?> {
        val body: MutableMap<String, Any> = LinkedHashMap()
        val path = request.getDescription(false)
        body["timestamp"] = Instant.now()
        body["status"] = status.value()
        body["errors"] = errors
        body["path"] = path
        body["message"] = status.reasonPhrase
        return ResponseEntity(body, status)
    }

    @ExceptionHandler(DomainException::class)
    fun handleDomainException(
            exception: DomainException
    ): ResponseEntity<DomainExceptionDto> {
        val response = DomainExceptionDto()
        response.code = exception.code
        response.message = exception.message
        response.status = exception.status
        return ResponseEntity(response, exception.status)
    }

}