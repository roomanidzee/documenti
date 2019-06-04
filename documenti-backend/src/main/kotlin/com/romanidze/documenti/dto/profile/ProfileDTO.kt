package com.romanidze.documenti.dto.profile

data class ProfileDTO(var surname: String?, var name: String?, var patronymic: String?){
    constructor(): this(null, null, null)
}