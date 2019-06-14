package com.romanidze.documenti.config.files

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("files.properties")
class FilesProperties {

    lateinit var storagePath: String
    lateinit var expiration: String

}