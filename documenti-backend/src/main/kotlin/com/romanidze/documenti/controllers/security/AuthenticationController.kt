package com.romanidze.documenti.controllers.security

import com.romanidze.documenti.dto.security.request.LoginDTO
import com.romanidze.documenti.dto.security.response.LoginResponseDTO
import com.romanidze.documenti.dto.security.response.TokenInfoDTO
import com.romanidze.documenti.services.interfaces.security.AuthenticationService

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@RestController
@RequestMapping("/security")
class AuthenticationController(private val authenticationService: AuthenticationService) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginDTO: LoginDTO): ResponseEntity<LoginResponseDTO>{
         return ResponseEntity.ok(this.authenticationService.loginUser(loginDTO))
    }

    @PostMapping("/check_auth")
    fun checkAuth(authentication: Authentication?): ResponseEntity<TokenInfoDTO>{
        return ResponseEntity.ok(this.authenticationService.checkToken(authentication))
    }

}