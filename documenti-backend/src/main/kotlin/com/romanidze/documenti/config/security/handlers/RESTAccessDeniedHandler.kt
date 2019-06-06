package com.romanidze.documenti.config.security.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.romanidze.documenti.config.security.errors.ServiceError

import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.io.PrintWriter


@Component("restAccessDenied")
class RESTAccessDeniedHandler(private val objectMapper: ObjectMapper): AccessDeniedHandler {

    override fun handle(req: HttpServletRequest?, resp: HttpServletResponse?, ex: AccessDeniedException?) {

        resp!!.contentType = "application/json"
        resp.status = HttpServletResponse.SC_FORBIDDEN
        resp.characterEncoding = "UTF-8"

        val serviceError = ServiceError(HttpStatus.FORBIDDEN)
        serviceError.message = "У вас нет доступа к URL: ${req!!.requestURL}"
        serviceError.debugMessage = ex!!.message

        val jsonResponse: String = this.objectMapper.writeValueAsString(serviceError)

        val pw: PrintWriter = resp.writer
        pw.println(jsonResponse)

    }
}