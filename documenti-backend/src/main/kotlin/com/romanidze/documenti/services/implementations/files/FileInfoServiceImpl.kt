package com.romanidze.documenti.services.implementations.files

import com.romanidze.documenti.components.FileStorageComponent
import com.romanidze.documenti.domain.postgres.FileInfo
import com.romanidze.documenti.domain.postgres.FileToNote
import com.romanidze.documenti.domain.postgres.FileToUser
import com.romanidze.documenti.dto.files.FileInfoDTO
import com.romanidze.documenti.mappers.mapstruct.FileInfoMapper
import com.romanidze.documenti.mappers.mybatis.FileInfoDBMapper
import com.romanidze.documenti.mappers.mybatis.FileToNoteDBMapper
import com.romanidze.documenti.mappers.mybatis.FileToUserDBMapper
import com.romanidze.documenti.services.interfaces.files.FileInfoService
import com.romanidze.documenti.services.interfaces.security.AuthenticationService

import org.apache.commons.io.IOUtils

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import java.io.File
import java.io.FileInputStream

import javax.servlet.http.HttpServletResponse

@Service
class FileInfoServiceImpl(private val fileInfoMapper: FileInfoMapper,
                          private val authenticationService: AuthenticationService,
                          private val fileInfoDBMapper: FileInfoDBMapper,
                          private val fileToNoteDBMapper: FileToNoteDBMapper,
                          private val fileToUserDBMapper: FileToUserDBMapper,
                          private val fileStorageComponent: FileStorageComponent): FileInfoService {

    override fun saveFile(authentication: Authentication?, multipartFile: MultipartFile): FileInfoDTO {

        //TODO: сделать проверку пользователя и файла

        val fileInfo: FileInfo = this.fileStorageComponent.convertFromMultipart(multipartFile)

        this.fileInfoDBMapper.save(fileInfo)
        this.fileStorageComponent.copyToStorage(multipartFile, fileInfo.encodedName!!)

        val user = this.authenticationService.getUserByAuthentication(authentication)

        val fileToUser = FileToUser(fileID = fileInfo.id, userID = user.id)
        this.fileToUserDBMapper.save(fileToUser)

        return this.fileInfoMapper.domainToDTO(fileInfo)

    }

    override fun writeFileToResponse(fileID: Long, resp: HttpServletResponse) {

        val file: FileInfo = this.fileInfoDBMapper.findByID(fileID)
        resp.contentType = file.fileType

        val inputStream = FileInputStream(File(file.fileURL))
        IOUtils.copy(inputStream, resp.outputStream)

        resp.flushBuffer()

    }

    override fun addFilesToNote(authentication: Authentication?, noteID: Long, files: Array<MultipartFile>) {

        //TODO: сделать проверку пользователя , файла и записи

        files.forEach {

            val fileToSave = this.fileStorageComponent.convertFromMultipart(it)
            this.fileInfoDBMapper.save(fileToSave)
            this.fileStorageComponent.copyToStorage(it, fileToSave.encodedName!!)

            val fileToNote = FileToNote(noteID = noteID,
                                        fileID = fileToSave.id)
            this.fileToNoteDBMapper.save(fileToNote)

        }

    }
}