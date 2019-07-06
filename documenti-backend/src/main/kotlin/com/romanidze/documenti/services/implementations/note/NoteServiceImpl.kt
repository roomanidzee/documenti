package com.romanidze.documenti.services.implementations.note

import com.romanidze.documenti.domain.postgres.Note
import com.romanidze.documenti.domain.postgres.Profile
import com.romanidze.documenti.domain.postgres.User
import com.romanidze.documenti.dto.note.NoteCreationDTO
import com.romanidze.documenti.dto.note.NoteDTO
import com.romanidze.documenti.mappers.mapstruct.NoteMapper
import com.romanidze.documenti.mappers.mybatis.NoteDBMapper
import com.romanidze.documenti.mappers.mybatis.ProfileDBMapper
import com.romanidze.documenti.services.interfaces.note.NoteService
import com.romanidze.documenti.services.interfaces.security.AuthenticationService

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class NoteServiceImpl(private val authenticationService: AuthenticationService,
                      private val profileDBMapper: ProfileDBMapper,
                      private val noteDBMapper: NoteDBMapper,
                      private val noteMapper: NoteMapper): NoteService {
    override fun createNote(authentication: Authentication?, note: NoteCreationDTO): NoteDTO {

        val user: User = this.authenticationService.getUserByAuthentication(authentication)

        val creationTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"))

        this.noteDBMapper.save(Note(userID=user.id,
                                    title = note.title,
                                    description = note.description,
                                    createdTime = creationTime))

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")

        return NoteDTO(title=note.title,
                       description = note.description,
                       createdTime = creationTime.format(formatter))

    }

    override fun retrieveUserNotes(authentication: Authentication?): List<NoteDTO> {

        val user: User = this.authenticationService.getUserByAuthentication(authentication)
        val profile: Profile = this.profileDBMapper.findByUserID(user.id!!)

        val notes: List<Note> = this.noteDBMapper.findByUserID(profile.id!!)

        return notes.map(this.noteMapper::domainToDTO)

    }
}