package com.romanidze.documenti.services.implementations.profile

import com.romanidze.documenti.domain.postgres.Profile
import com.romanidze.documenti.domain.postgres.User
import com.romanidze.documenti.dto.profile.ProfileCreationDTO
import com.romanidze.documenti.dto.profile.ProfileDTO
import com.romanidze.documenti.mappers.mapstruct.ProfileMapper
import com.romanidze.documenti.mappers.mybatis.ProfileDBMapper
import com.romanidze.documenti.services.interfaces.profile.ProfileService
import com.romanidze.documenti.services.interfaces.security.AuthenticationService

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

import java.time.LocalDateTime
import java.time.ZoneId

@Service
class ProfileServiceImpl(private val profileMapper: ProfileMapper,
                         private val profileDBMapper: ProfileDBMapper,
                         private val authenticationService: AuthenticationService): ProfileService {

    override fun retrieveProfile(authentication: Authentication?): ProfileDTO {

        val user: User = this.authenticationService.getUserByAuthentication(authentication)
        val profile: Profile = this.profileDBMapper.findByUserID(user.id!!)

        return this.profileMapper.domainToDTO(profile)

    }

    override fun createProfile(profileDTO: ProfileCreationDTO): ProfileDTO {

        val profile = Profile(userID = profileDTO.userID,
                              surname = profileDTO.surname,
                              name = profileDTO.name,
                              patronymic = profileDTO.patronymic,
                              email = profileDTO.email,
                              phoneNumber = profileDTO.phoneNumber,
                              createdTime = LocalDateTime.now(ZoneId.of("Europe/Moscow")))

        this.profileDBMapper.save(profile)

        return this.profileMapper.domainToDTO(profile)

    }
}