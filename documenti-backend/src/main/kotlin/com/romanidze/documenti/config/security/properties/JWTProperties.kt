package com.romanidze.documenti.config.security.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("jwt.properties")
class JWTProperties {

    lateinit var header: String
    lateinit var secretKey: String
    lateinit var expiresAt: String

}