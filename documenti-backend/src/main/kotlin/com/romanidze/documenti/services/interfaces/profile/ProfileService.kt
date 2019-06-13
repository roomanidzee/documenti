package com.romanidze.documenti.services.interfaces.profile

import com.romanidze.documenti.dto.profile.ProfileCreationDTO
import com.romanidze.documenti.dto.profile.ProfileDTO

interface ProfileService {

    fun createProfile(profileDTO: ProfileCreationDTO): ProfileDTO

}