package com.romanidze.documenti.controllers.security

import com.romanidze.documenti.dto.security.request.RegistrationDTO
import com.romanidze.documenti.dto.security.response.RegistrationResponseDTO
import com.romanidze.documenti.services.interfaces.security.RegistrationService

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@RestController
@RequestMapping("/security")
class RegistrationController(private val registrationService: RegistrationService) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody registrationDTO: RegistrationDTO): ResponseEntity<RegistrationResponseDTO>{
        return ResponseEntity.ok(this.registrationService.registerUser(registrationDTO))
    }

}