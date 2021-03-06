package com.romanidze.documenti.dto.admin

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserAdminDTO(
        @NotNull(message = "Поле username не должно быть null")
        @NotBlank(message = "Поле username не должно быть пустой строкой")
        var username: String?,

        @NotNull(message = "Поле password не должно быть null")
        @NotBlank(message = "Поле password не должно быть пустой строкой")
        var password: String?,

        @NotNull(message = "Поле role не должно быть null")
        @NotBlank(message = "Поле role не должно быть пустой строкой")
        var role: String?,

        @NotNull(message = "Поле state не должно быть null")
        @NotBlank(message = "Поле state не должно быть пустой строкой")
        var state: String?){
    constructor() : this(null, null, null, null)
}