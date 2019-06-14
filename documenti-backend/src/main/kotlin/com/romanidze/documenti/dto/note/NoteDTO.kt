package com.romanidze.documenti.dto.note

data class NoteDTO(var title: String?, var description: String?, var createdTime: String?) {

    constructor(): this(null, null, null)

}