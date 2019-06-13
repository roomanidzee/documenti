package com.romanidze.documenti.domain.postgres

import java.time.LocalDateTime

data class Profile(var id: Long? = null, var userID: Long?, var surname: String?, var name: String?,
                   var patronymic: String?, var email: String?, var phoneNumber: String?, var createdTime: LocalDateTime?) {
    constructor(): this(null, null, null, null,
            null, null, null, null)
}