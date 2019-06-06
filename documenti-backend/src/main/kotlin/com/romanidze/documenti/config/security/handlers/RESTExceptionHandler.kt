package com.romanidze.documenti.config.security.handlers

import com.romanidze.documenti.config.security.errors.ServiceError
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class RESTExceptionHandler: ResponseEntityExceptionHandler() {

    companion object{
        val logger: Logger = LogManager.getLogger(RESTExceptionHandler::class)
    }


    override fun handleMissingServletRequestParameter(ex: MissingServletRequestParameterException,
                                                      headers: HttpHeaders,
                                                      status: HttpStatus,
                                                      request: WebRequest): ResponseEntity<Any> {

        val errorMessage = "Отсутствует параметр ${ex.parameterName} типа ${ex.parameterType}"
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.BAD_REQUEST, errorMessage, ex)

        return ResponseEntity(serviceError, serviceError.status!!)

    }

    override fun handleHttpMediaTypeNotSupported(ex: HttpMediaTypeNotSupportedException,
                                                 headers: HttpHeaders,
                                                 status: HttpStatus,
                                                 request: WebRequest): ResponseEntity<Any> {

        val sb = StringBuilder()

        sb.append("Для данного запроса тип мультимедиа ").append(ex.contentType).append(" не поддерживается.")
          .append("Поддерживаемые типы: ")

        ex.supportedMediaTypes
          .forEach {
              sb.append(it).append(", ")
          }

        val errorMessage = sb.substring(0, sb.length - 2)
        logger.error(errorMessage)

        val serviceError = ServiceError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex)

        return ResponseEntity(serviceError, serviceError.status!!)

    }

}