package com.romanidze.documenti.config.security

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer
import org.springframework.web.multipart.support.MultipartFilter

import javax.servlet.ServletContext

class SecurityConfigInitializer: AbstractSecurityWebApplicationInitializer() {

    override fun beforeSpringSecurityFilterChain(servletContext: ServletContext?) {
        this.insertFilters(servletContext, MultipartFilter())
    }

}