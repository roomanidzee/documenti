package com.romanidze.documenti.mappers.mapstruct

import com.romanidze.documenti.config.mapstruct.MapStructConfig
import com.romanidze.documenti.domain.Profile
import com.romanidze.documenti.dto.ProfileDTO

import org.mapstruct.Mapper

@Mapper(config=MapStructConfig::class)
interface ProfileMapper {

    fun domainToDTO(profile: Profile): ProfileDTO
    fun dtoToDomain(profileDTO: ProfileDTO): Profile

}