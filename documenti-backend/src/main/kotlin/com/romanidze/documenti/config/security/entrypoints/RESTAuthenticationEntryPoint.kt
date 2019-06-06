package com.romanidze.documenti.config.security.entrypoints

import com.fasterxml.jackson.databind.ObjectMapper

import com.romanidze.documenti.config.security.errors.ServiceError

import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import java.io.PrintWriter

@Component("restAuthenticationEntryPoint")
class RESTAuthenticationEntryPoint(private val objectMapper: ObjectMapper): AuthenticationEntryPoint {

    override fun commence(req: HttpServletRequest?, resp: HttpServletResponse?, ex: AuthenticationException?) {

        resp!!.contentType = "application/json"
        resp.status = HttpServletResponse.SC_UNAUTHORIZED
        resp.characterEncoding = "UTF-8"

        val serviceError = ServiceError(HttpStatus.UNAUTHORIZED)
        serviceError.message = "Вы не авторизованы для URL: ${req!!.requestURL}"
        serviceError.debugMessage = ex!!.message

        val jsonResponse: String = this.objectMapper.writeValueAsString(serviceError)

        val pw: PrintWriter = resp.writer
        pw.println(jsonResponse)

    }
}