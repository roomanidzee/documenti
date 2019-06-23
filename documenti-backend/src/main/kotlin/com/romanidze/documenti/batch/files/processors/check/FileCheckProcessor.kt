package com.romanidze.documenti.batch.files.processors.check

import com.romanidze.documenti.dto.files.FileValidationDTO

import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class FileCheckProcessor: ItemProcessor<FileValidationDTO, FileValidationDTO> {

    override fun process(item: FileValidationDTO): FileValidationDTO? {
        return item
    }
}