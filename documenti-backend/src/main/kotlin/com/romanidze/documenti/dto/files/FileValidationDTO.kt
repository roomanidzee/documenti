package com.romanidze.documenti.dto.files

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name="file")
@XmlAccessorType(XmlAccessType.FIELD)
data class FileValidationDTO(@field:XmlElement(name="type")
                             val fileType: String,
                             @field:XmlElement(name="expiration")
                             val fileExpirationTime: String)