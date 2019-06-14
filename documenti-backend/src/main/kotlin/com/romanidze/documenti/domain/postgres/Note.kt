package com.romanidze.documenti.domain.postgres

import java.time.LocalDateTime

data class Note(var id: Long? = null, var userID: Long?, var title: String?,
                var description: String?, var createdTime: LocalDateTime?){
    constructor(): this(null, null, null, null, null)
}