package com.romanidze.documenti.config.security.filters

import com.romanidze.documenti.config.security.authentication.JWTTokenAuthentication
import com.romanidze.documenti.config.security.properties.JWTProperties

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class JWTTokenAuthenticationFilter(private val jwtProperties: JWTProperties): Filter {

    override fun doFilter(req: ServletRequest?, resp: ServletResponse?, chain: FilterChain?) {

        val request: HttpServletRequest = req as HttpServletRequest

        val token: String? = request.getHeader(this.jwtProperties.header)

        val authentication: JWTTokenAuthentication?

        when (token) {
            null -> {
                authentication = JWTTokenAuthentication(null)
                authentication.isAuthenticated = false
            }
            else -> {
                authentication = JWTTokenAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        chain!!.doFilter(req, resp)

    }

}