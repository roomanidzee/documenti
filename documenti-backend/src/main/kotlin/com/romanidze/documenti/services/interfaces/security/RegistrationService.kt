package com.romanidze.documenti.services.interfaces.security

import com.romanidze.documenti.dto.security.request.RegistrationDTO
import com.romanidze.documenti.dto.security.response.RegistrationResponseDTO

interface RegistrationService {

    fun registerUser(registrationDTO: RegistrationDTO): RegistrationResponseDTO

}