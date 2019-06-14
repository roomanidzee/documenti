package com.romanidze.documenti.services.interfaces.note

import com.romanidze.documenti.dto.note.NoteDTO
import org.springframework.security.core.Authentication

interface NoteService {

    fun retrieveUserNotes(authentication: Authentication?): List<NoteDTO>

}