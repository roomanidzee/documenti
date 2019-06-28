package com.romanidze.documenti.batch.files.writers.check

import com.romanidze.documenti.domain.postgres.FileInfo
import com.romanidze.documenti.dto.files.FileValidationDTO
import com.romanidze.documenti.mappers.mybatis.FileInfoDBMapper
import com.romanidze.documenti.services.interfaces.files.FileInfoService

import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

import java.time.LocalDateTime
import java.time.ZoneId

@Component
class FileCheckWriter(private val fileInfoDBMapper: FileInfoDBMapper,
                      private val fileInfoService: FileInfoService): ItemWriter<FileValidationDTO> {

    override fun write(items: MutableList<out FileValidationDTO>) {

        val removableList = mutableListOf<FileInfo>()
        val allFiles = this.fileInfoDBMapper.findAll()
        val zoneID = ZoneId.of("Europe/Moscow")
        val currentTime = LocalDateTime.now().atZone(zoneID).toEpochSecond()

        items.forEach { item ->

            val expirationTime = item.fileExpirationTime.toLong()

            allFiles.forEach { file ->
                val fileCreationTime = file.createdTime!!.atZone(zoneID).toEpochSecond()

                if((file.fileType!! == item.fileType) && (fileCreationTime - currentTime > expirationTime)){
                    removableList += file
                }
            }
        }

        this.fileInfoService.removeManyFiles(removableList)

    }
}