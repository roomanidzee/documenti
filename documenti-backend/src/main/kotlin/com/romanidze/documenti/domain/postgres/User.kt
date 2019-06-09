package com.romanidze.documenti.domain.postgres

data class User(var id: Long? = null, var username: String?, var password: String?, var role: String?, var state: String?){
    constructor() : this(null, null, null, null, null)
}