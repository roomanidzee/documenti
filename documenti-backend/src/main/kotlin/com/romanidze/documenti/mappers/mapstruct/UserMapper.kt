package com.romanidze.documenti.mappers.mapstruct

import com.romanidze.documenti.config.mapstruct.MapStructConfig
import com.romanidze.documenti.domain.postgres.User
import com.romanidze.documenti.dto.admin.UserAdminDTO

import org.mapstruct.Mapper

@Mapper(config=MapStructConfig::class)
interface UserMapper {

    fun domainToDTO(user: User): UserAdminDTO
    fun dtoToDomain(userAdminDTO: UserAdminDTO): User

}