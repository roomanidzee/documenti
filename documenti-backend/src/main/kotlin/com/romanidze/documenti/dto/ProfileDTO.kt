package com.romanidze.documenti.dto

data class ProfileDTO(var surname: String?, var name: String?, var patronymic: String?){
    constructor(): this(null, null, null)
}