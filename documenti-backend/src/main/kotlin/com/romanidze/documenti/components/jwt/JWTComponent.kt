package com.romanidze.documenti.components.jwt

import com.romanidze.documenti.config.security.authentication.JWTTokenAuthentication
import com.romanidze.documenti.config.security.properties.JWTProperties
import com.romanidze.documenti.domain.postgres.User

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm

import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.stereotype.Component

import java.util.Date

@Component
class JWTComponent(private val jwtProperties: JWTProperties) {

    fun retrieveToken(authentication: JWTTokenAuthentication): Claims{

        val body: Claims

        try{

            body = Jwts.parser()
                       .setSigningKey(this.jwtProperties.secretKey)
                       .parseClaimsJws(authentication.name)
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

        return body

    }

    fun generateToken(user: User, expirationTime: Date): String{

        return Jwts.builder()
                   .setSubject(user.id.toString())
                   .setExpiration(expirationTime)
                   .claim("role", user.role)
                   .claim("state", user.state)
                   .claim("username", user.username)
                   .signWith(SignatureAlgorithm.HS512, this.jwtProperties.secretKey)
                   .compact()

    }

}