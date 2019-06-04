package com.romanidze.documenti.config.security.providers

import com.romanidze.documenti.config.security.authentication.JWTTokenAuthentication
import com.romanidze.documenti.config.security.details.UserDetailsImpl
import com.romanidze.documenti.config.security.properties.JWTProperties

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component


@Component
class JWTTokenAuthenticationProvider(private val jwtProperties: JWTProperties): AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {

        val tokenAuthentication: JWTTokenAuthentication = authentication as JWTTokenAuthentication

        val body: Claims

        try{

            body = Jwts.parser()
                       .setSigningKey(this.jwtProperties.secretKey)
                       .parseClaimsJws(tokenAuthentication.name)
                       .body

        }catch(e: Exception){

            when(e){
                is MalformedJwtException -> {
                    throw AuthenticationServiceException("JWT - токен сформирован неверно по причине: $e")
                }
                else ->{
                    throw AuthenticationServiceException("При работе с JWT - токеном произошла ошибка: $e")
                }
            }

        }

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