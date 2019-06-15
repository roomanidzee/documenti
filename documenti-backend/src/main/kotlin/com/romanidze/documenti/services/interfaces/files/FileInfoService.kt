package com.romanidze.documenti.services.interfaces.files

import com.romanidze.documenti.dto.files.FileInfoDTO

import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletResponse

interface FileInfoService {

    fun saveFile(authentication: Authentication?, multipartFile: MultipartFile): FileInfoDTO
    fun writeFileToResponse(fileID: Long, resp: HttpServletResponse)
    fun addFilesToNote(authentication: Authentication?, noteID: Long, files: Array<MultipartFile>)

}