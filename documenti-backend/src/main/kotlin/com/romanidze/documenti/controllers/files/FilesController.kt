package com.romanidze.documenti.controllers.files

import com.romanidze.documenti.dto.files.FileInfoDTO
import com.romanidze.documenti.dto.utils.MessageResponseDTO
import com.romanidze.documenti.services.interfaces.files.FileInfoService

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletResponse

import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/files")
class FilesController(private val fileInfoService: FileInfoService) {

    @PostMapping("/save_user_file", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun saveUserFile(authentication: Authentication?,
                     @RequestPart("file")
                     @Valid @NotNull @NotBlank
                     file: MultipartFile): ResponseEntity<FileInfoDTO>{
        return ResponseEntity.ok(this.fileInfoService.saveFile(authentication, file))
    }

    @GetMapping("/{id}")
    fun getFile(authentication: Authentication?,
                @PathVariable("id") id: Long,
                resp: HttpServletResponse){
        this.fileInfoService.writeFileToResponse(id, resp)
    }

    @PostMapping("/add/{note_id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun addFilesForNote(authentication: Authentication?,
                        @PathVariable("note_id") noteID: Long,
                        @RequestPart("files")
                        @Valid @NotNull @NotBlank
                        files: Array<MultipartFile>): ResponseEntity<MessageResponseDTO>{
        this.fileInfoService.addFilesToNote(authentication, noteID, files)
        return ResponseEntity.ok(MessageResponseDTO(message = "Файлы добавлены к записи с ID $noteID"))
    }
}