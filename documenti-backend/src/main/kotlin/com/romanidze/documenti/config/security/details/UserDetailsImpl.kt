package com.romanidze.documenti.config.security.details

import com.romanidze.documenti.config.security.enums.Role
import com.romanidze.documenti.config.security.enums.UserState
import com.romanidze.documenti.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(val user: User?): UserDetails {

    //private var id: Long
    //private var role: String
    //private var state: String
    //private var username: String

    constructor(id: Long, role: String, state: String, username: String) : this(null) {

       val roleArray = arrayOf<String>()

       for(i in 0..Role.values().size){
           roleArray[i] = Role.values()[i].toString()
       }

       val roleCondition: Boolean = roleArray.any { item -> item == role }

       val stateArray = arrayOf<String>()

       for(i in 0..UserState.values().size){
           stateArray[i] = UserState.values()[i].toString()
       }

       val stateCondition: Boolean = stateArray.any {item -> item == state}

    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEnabled(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUsername(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPassword(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isAccountNonExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isAccountNonLocked(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}