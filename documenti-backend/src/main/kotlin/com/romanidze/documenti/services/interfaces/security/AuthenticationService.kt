package com.romanidze.documenti.services.interfaces.security

import com.romanidze.documenti.domain.postgres.User
import com.romanidze.documenti.dto.security.request.LoginDTO
import com.romanidze.documenti.dto.security.response.LoginResponseDTO
import com.romanidze.documenti.dto.security.response.TokenInfoDTO

import org.springframework.security.core.Authentication

interface AuthenticationService {

    fun getUserByAuthentication(authentication: Authentication?): User
    fun checkToken(authentication: Authentication?): TokenInfoDTO
    fun loginUser(loginDTO: LoginDTO): LoginResponseDTO

}