package com.romanidze.documenti.config.security.authentication

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JWTTokenAuthentication: Authentication {

    private var isAuthenticated: Boolean = false
    private var userDetails: UserDetails? = null
    private var token: String? = null

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.userDetails!!.authorities
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.isAuthenticated = isAuthenticated
    }

    override fun getName(): String {
        return this.token!!
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return null
    }

    override fun isAuthenticated(): Boolean {
        return this.isAuthenticated
    }

    override fun getDetails(): UserDetails? {
        return this.userDetails
    }
}