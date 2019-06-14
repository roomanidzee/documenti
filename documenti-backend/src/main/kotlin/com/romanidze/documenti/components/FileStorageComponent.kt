package com.romanidze.documenti.components

import com.romanidze.documenti.config.files.FilesProperties
import com.romanidze.documenti.domain.postgres.FileInfo

import org.apache.commons.io.FilenameUtils

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.ZoneId

import java.util.UUID

@Component
class FileStorageComponent(private val filesProperties: FilesProperties) {

    private fun getURLOfFile(storageFileName: String): String {
        return "${this.filesProperties.storagePath}/$storageFileName"
    }

    private fun createEncodedFileName(originalFileName: String): String {

        val extension: String = FilenameUtils.getExtension(originalFileName)
        val newFileName: String = UUID.randomUUID().toString()

        return "$newFileName.$extension"

    }

    fun copyToStorage(file: MultipartFile, storageFileName: String){
        Files.copy(file.inputStream, Paths.get(filesProperties.storagePath, storageFileName))
    }

    fun convertFromMultipart(file: MultipartFile): FileInfo {

        val originalFileName: String = file.originalFilename!!
        val fileType: String = file.contentType!!
        val fileSize: Long = file.size
        val storageFileName: String = this.createEncodedFileName(originalFileName)
        val fileURL: String = this.getURLOfFile(storageFileName)

        return FileInfo(originalName = originalFileName,
                        encodedName = storageFileName,
                        fileSize = fileSize,
                        fileType = fileType,
                        fileURL = fileURL,
                        createdTime = LocalDateTime.now(ZoneId.of("Europe/Moscow")))

    }

}