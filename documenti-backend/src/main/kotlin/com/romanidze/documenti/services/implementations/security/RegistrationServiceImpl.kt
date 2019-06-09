package com.romanidze.documenti.services.implementations.security

import com.romanidze.documenti.config.security.enums.Role
import com.romanidze.documenti.config.security.enums.UserState
import com.romanidze.documenti.domain.postgres.User
import com.romanidze.documenti.dto.security.request.RegistrationDTO
import com.romanidze.documenti.dto.security.response.RegistrationResponseDTO
import com.romanidze.documenti.mappers.mybatis.UserDBMapper
import com.romanidze.documenti.services.interfaces.security.RegistrationService

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegistrationServiceImpl(private val passwordEncoder: PasswordEncoder,
                              private val userDBMapper: UserDBMapper): RegistrationService {

    override fun registerUser(registrationDTO: RegistrationDTO): RegistrationResponseDTO {

        val newUser = User(username = registrationDTO.username,
                           password = this.passwordEncoder.encode(registrationDTO.password),
                           role = Role.USER.toString(),
                           state = UserState.CONFIRMED.toString())

        this.userDBMapper.save(newUser)

        return RegistrationResponseDTO(newUser.username!!, newUser.state!!)

    }

}