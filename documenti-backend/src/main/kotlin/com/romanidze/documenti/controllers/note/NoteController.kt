package com.romanidze.documenti.controllers.note

import com.romanidze.documenti.dto.note.NoteDTO
import com.romanidze.documenti.services.interfaces.note.NoteService

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/notes")
class NoteController(private val noteService: NoteService) {

    @GetMapping("/show")
    fun getUserNotes(authentication: Authentication): ResponseEntity<List<NoteDTO>>{
        return ResponseEntity.ok(this.noteService.retrieveUserNotes(authentication))
    }

}