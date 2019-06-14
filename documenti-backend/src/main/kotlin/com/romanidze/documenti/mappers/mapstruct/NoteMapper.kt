package com.romanidze.documenti.mappers.mapstruct

import com.romanidze.documenti.config.mapstruct.MapStructConfig
import com.romanidze.documenti.domain.postgres.Note
import com.romanidze.documenti.dto.note.NoteDTO

import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(config = MapStructConfig::class)
interface NoteMapper {

    @Mapping(source = "createdTime", target = "createdTime", dateFormat = "dd.MM.yyyy HH:mm:ss")
    fun domainToDTO(note: Note): NoteDTO

    @Mapping(source = "createdTime", target = "createdTime", dateFormat = "dd.MM.yyyy HH:mm:ss")
    fun dtoToDomain(noteDTO: NoteDTO): Note

}