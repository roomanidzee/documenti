package com.romanidze.documenti.config.security.errors

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError

import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@JsonTypeName("service_error")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT ,use = JsonTypeInfo.Id.NAME)
public class ServiceError() {

    @JsonProperty("status_code")
    var status: HttpStatus? = null

    @JsonProperty("error_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy hh:mm:ss")
    var errorTime: LocalDateTime? = null

    @JsonProperty("message")
    var message: String? = null

    @JsonProperty("debug_message")
    var debugMessage: String? = null

    @JsonProperty("sub_errors")
    var subErrors: MutableList<APISubError>? = null

    constructor(status: HttpStatus): this() {

        this.errorTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"))
        this.status = status

    }

    constructor(status: HttpStatus, ex: Throwable): this() {

        this.errorTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"))
        this.status = status
        this.message = "Неожиданная ошибка"
        this.debugMessage = ex.localizedMessage

    }

    constructor(status: HttpStatus, message: String, ex: Throwable): this() {

        this.errorTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"))
        this.status = status
        this.message = message
        this.debugMessage = ex.localizedMessage

    }

    private fun addSubError(subError: APISubError): Unit {

        if(this.subErrors == null){
            this.subErrors = ArrayList()
        }

        this.subErrors!!.add(subError)

    }

    private fun addValidationError(objectError: String, field: String, rejectedValue: JvmType.Object, message: String) =
            this.addSubError(APIValidationError(objectError, field, rejectedValue, message))

    private fun addValidationError(objectError: String, message: String) =
            this.addSubError(APIValidationError(objectError, message))

    private fun addValidationError(fieldError: FieldError) = this.addValidationError(fieldError.objectName,
                                                                                     fieldError.field,
                                                                                     fieldError.rejectedValue
                                                                                             as JvmType.Object,
                                                                                     fieldError.defaultMessage!!)

    public fun addValidationFieldErrors(fieldErrors: List<FieldError>) = fieldErrors.forEach(this::addValidationError)

    public fun addValidationError(objectError: ObjectError) =
            this.addValidationError(objectError.objectName,
                                    objectError.defaultMessage!!)

    public fun addValidationObjectErrors(objectErrors: List<ObjectError>) = objectErrors.forEach(this::addValidationError)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ServiceError) return false

        if (status != other.status) return false
        if (errorTime != other.errorTime) return false
        if (message != other.message) return false
        if (debugMessage != other.debugMessage) return false
        if (subErrors != other.subErrors) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status?.hashCode() ?: 0
        result = 31 * result + (errorTime?.hashCode() ?: 0)
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (debugMessage?.hashCode() ?: 0)
        result = 31 * result + (subErrors?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "ServiceError(status=$status, errorTime=$errorTime, " +
                "message=$message, debugMessage=$debugMessage, subErrors=$subErrors)"
    }

}