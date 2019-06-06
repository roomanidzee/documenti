package com.romanidze.documenti.config.security.errors

import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class APIValidationError(
        @JsonProperty("object")
        var objectError: String?,

        @JsonProperty("message")
        var message: String?) : APISubError() {

    @JsonProperty("rejected_value")
    var rejectedValue: JvmType.Object? = null

    @JsonProperty("field")
    var field: String? = null

    constructor(objectError: String, field: String, rejectedValue: JvmType.Object, message: String)
                                                              : this(null, null){

        this.objectError = objectError
        this.field = field
        this.rejectedValue = rejectedValue
        this.message = message

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is APIValidationError) return false

        if (objectError != other.objectError) return false
        if (message != other.message) return false
        if (rejectedValue != other.rejectedValue) return false
        if (field != other.field) return false

        return true
    }

    override fun hashCode(): Int {
        var result = objectError?.hashCode() ?: 0
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (rejectedValue?.hashCode() ?: 0)
        result = 31 * result + (field?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "APIValidationError(objectError=$objectError, message=$message, " +
                "rejectedValue=$rejectedValue, field=$field)"
    }

}