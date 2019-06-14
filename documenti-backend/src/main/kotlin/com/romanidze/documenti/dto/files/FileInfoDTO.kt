package com.romanidze.documenti.dto.files

data class FileInfoDTO(var originalName: String?, var fileSize: Long?, var createdTime: String?, var fileURL: String?) {
    constructor(): this(null, null, null, null)
}