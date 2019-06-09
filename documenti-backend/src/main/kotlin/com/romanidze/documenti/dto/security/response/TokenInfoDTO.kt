package com.romanidze.documenti.dto.security.response

data class TokenInfoDTO(val token: String, val expired: Boolean)