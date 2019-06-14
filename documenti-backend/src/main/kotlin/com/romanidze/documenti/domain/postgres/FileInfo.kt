package com.romanidze.documenti.domain.postgres

import java.time.LocalDateTime

data class FileInfo(var id: Long? = null, var originalName: String?, var encodedName: String?,
                    var fileSize: Long?, var fileType: String?, var fileURL: String?, var createdTime: LocalDateTime?) {

    constructor(): this(null, null, null, null, null, null, null)

}