package com.romanidze.documenti.config.security.providers

import com.romanidze.documenti.components.JWTComponent
import com.romanidze.documenti.config.security.authentication.JWTTokenAuthentication
import com.romanidze.documenti.config.security.details.UserDetailsImpl

import io.jsonwebtoken.Claims

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component


@Component
class JWTTokenAuthenticationProvider(private val jwtComponent: JWTComponent): AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {

        val tokenAuthentication: JWTTokenAuthentication = authentication as JWTTokenAuthentication

        val body: Claims = this.jwtComponent.retrieveToken(tokenAuthentication)

        val userDetails: UserDetails = UserDetailsImpl(
                id = body["sub"].toString().toLong(),
                role = body["role"].toString(),
                state = body["state"].toString(),
                username = body["username"].toString()
        )

        tokenAuthentication.setUserDetails(userDetails)
        tokenAuthentication.isAuthenticated = true

        return tokenAuthentication

    }

    override fun supports(authentication: Class<*>?): Boolean {
        return JWTTokenAuthentication::class == authentication
    }
}