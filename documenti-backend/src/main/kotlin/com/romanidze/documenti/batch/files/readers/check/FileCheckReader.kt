package com.romanidze.documenti.batch.files.readers.check

import com.romanidze.documenti.dto.files.FileValidationDTO

import org.springframework.batch.item.xml.StaxEventItemReader
import org.springframework.core.io.Resource
import org.springframework.oxm.jaxb.Jaxb2Marshaller

class FileCheckReader(private val resource: Resource) : StaxEventItemReader<FileValidationDTO>() {

    init {
        setResource(resource)
        setFragmentRootElementName("file")
        val fileMarshaller = Jaxb2Marshaller()
        fileMarshaller.setClassesToBeBound(FileValidationDTO::class.java)
        setUnmarshaller(fileMarshaller)
    }

}