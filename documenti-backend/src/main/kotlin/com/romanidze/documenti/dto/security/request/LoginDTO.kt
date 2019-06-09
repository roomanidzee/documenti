package com.romanidze.documenti.dto.security.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
class LoginDTO(
        @NotNull(message = "Поле username не должно быть null")
        @NotBlank(message = "Поле username не должно быть пустой строкой")
        var username: String?,

        @NotNull(message = "Поле password не должно быть null")
        @NotBlank(message = "Поле password не должно быть пустой строкой")
        var password: String?)