package com.romanidze.documenti.mappers.mapstruct

import com.romanidze.documenti.config.mapstruct.MapStructConfig
import com.romanidze.documenti.domain.User
import com.romanidze.documenti.dto.UserDTO

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(config=MapStructConfig::class)
interface UserMapper {

    fun domainToDTO(user: User): UserDTO

    @Mapping(source="id", target = "id", ignore = true)
    fun dtoToDomain(userDTO: UserDTO): User

}