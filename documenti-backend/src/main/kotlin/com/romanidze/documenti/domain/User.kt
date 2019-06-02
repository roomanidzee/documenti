package com.romanidze.documenti.domain

data class User(var id: Long?, var username: String?, var password: String?, var role: String?, var state: String?){
    constructor() : this(null, null, null, null, null)
}