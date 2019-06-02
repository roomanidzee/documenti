package com.romanidze.documenti.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserDTO(var id: Long?, var username: String?, var password: String?){
    constructor() : this(null, null, null)
}