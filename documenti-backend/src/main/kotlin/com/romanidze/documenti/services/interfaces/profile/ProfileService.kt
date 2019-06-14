package com.romanidze.documenti.services.interfaces.profile

import com.romanidze.documenti.dto.profile.ProfileCreationDTO
import com.romanidze.documenti.dto.profile.ProfileDTO
import org.springframework.security.core.Authentication

interface ProfileService {

    fun createProfile(profileDTO: ProfileCreationDTO): ProfileDTO
    fun retrieveProfile(authentication: Authentication?): ProfileDTO

}