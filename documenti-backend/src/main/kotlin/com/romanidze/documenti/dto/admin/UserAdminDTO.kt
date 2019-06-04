package com.romanidze.documenti.dto.admin

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserAdminDTO(var username: String?, var password: String?, var role: String?, var state: String?){
    constructor() : this(null, null, null, null)
}