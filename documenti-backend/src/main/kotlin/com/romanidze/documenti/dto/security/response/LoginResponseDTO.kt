package com.romanidze.documenti.dto.security.response

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponseDTO(val username: String, val token: String,
                            @JsonProperty("expire_time")
                            val expireTime: String)