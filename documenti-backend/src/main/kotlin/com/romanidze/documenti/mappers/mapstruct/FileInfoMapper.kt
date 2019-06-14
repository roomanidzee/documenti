package com.romanidze.documenti.mappers.mapstruct

import com.romanidze.documenti.config.mapstruct.MapStructConfig
import com.romanidze.documenti.domain.postgres.FileInfo
import com.romanidze.documenti.dto.files.FileInfoDTO

import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(config = MapStructConfig::class)
interface FileInfoMapper {

    @Mapping(source = "createdTime", target = "createdTime", dateFormat = "dd.MM.yyyy HH:mm:ss")
    fun domainToDTO(fileInfo: FileInfo): FileInfoDTO

    @Mapping(source = "createdTime", target = "createdTime", dateFormat = "dd.MM.yyyy HH:mm:ss")
    fun dtoToDomain(fileInfoDTO: FileInfoDTO): FileInfo

}