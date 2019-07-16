package com.romanidze.documenti.services.implementations.security

import com.romanidze.documenti.components.jwt.JWTComponent
import com.romanidze.documenti.config.security.authentication.JWTTokenAuthentication
import com.romanidze.documenti.config.security.properties.JWTProperties
import com.romanidze.documenti.domain.postgres.User
import com.romanidze.documenti.dto.security.request.LoginDTO
import com.romanidze.documenti.dto.security.response.LoginResponseDTO
import com.romanidze.documenti.dto.security.response.TokenInfoDTO
import com.romanidze.documenti.mappers.mybatis.UserDBMapper
import com.romanidze.documenti.services.interfaces.security.AuthenticationService

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException

import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Service
class AuthenticationServiceImpl(private val userDBMapper: UserDBMapper,
                                private val passwordEncoder: PasswordEncoder,
                                private val jwtComponent: JWTComponent,
                                private val jwtProperties: JWTProperties): AuthenticationService {

    override fun getUserByAuthentication(authentication: Authentication?): User {

        val tokenAuthentication: JWTTokenAuthentication = when (authentication) {
            null -> SecurityContextHolder.getContext().authentication as JWTTokenAuthentication
            else -> authentication as JWTTokenAuthentication
        }

        val body: Claims = this.jwtComponent.retrieveToken(tokenAuthentication)
        val username = body["username"].toString()

        return userDBMapper.findByUsername(username) ?:
                            throw IllegalArgumentException("Пользователь с логином $username не найден")

    }

    override fun checkToken(authentication: Authentication?): TokenInfoDTO {

        val tokenAuthentication: JWTTokenAuthentication = when (authentication) {
            null -> SecurityContextHolder.getContext().authentication as JWTTokenAuthentication
            else -> authentication as JWTTokenAuthentication
        }

        var expired: Boolean

        try{

            val body: Claims = this.jwtComponent.retrieveToken(tokenAuthentication)
            body.subject

            expired = false
        }catch(e: Exception){

            when(e){
                is ExpiredJwtException -> {
                    expired = true
                }
                else ->{
                    throw AuthenticationServiceException("При работе с JWT - токеном произошла ошибка: $e")
                }
            }

        }

        return TokenInfoDTO(tokenAuthentication.name, expired)

    }

    override fun loginUser(loginDTO: LoginDTO): LoginResponseDTO {

        val username = loginDTO.username
        val password = loginDTO.password

        val user: User = userDBMapper.findByUsername(username!!) ?:
                                      throw IllegalArgumentException("Пользователь с логином $username не найден")

        if(this.passwordEncoder.matches(password, user.password)){

            val time: LocalDateTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"))
                                                   .plusSeconds(this.jwtProperties.expiresAt.toLong())

            val expirationTime: Date =
                    Date.from(time.atZone(ZoneId.of("Europe/Moscow")).toInstant())
            val token: String = this.jwtComponent.generateToken(user, expirationTime)

            val dateFormat = "dd.MM.yyyy HH:mm:ss"
            val df = SimpleDateFormat(dateFormat)

            return LoginResponseDTO(user.username!!, token, df.format(expirationTime))

        }else{
            throw IllegalArgumentException("Пользователь с таким логином и паролем не найден")
        }

    }
}