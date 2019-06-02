package com.romanidze.documenti.domain

data class User(var id: Long?, var username: String?, var password: String?){
    constructor() : this(null, null, null)
}