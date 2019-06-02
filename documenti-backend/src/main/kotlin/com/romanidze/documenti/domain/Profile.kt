package com.romanidze.documenti.domain

data class Profile(var id: Long?, var userID: Long?, var surname: String?, var name: String?,
                   var patronymic: String?, var email: String?, var phoneNumber: String?) {
    constructor(): this(null, null, null, null, null, null, null)
}