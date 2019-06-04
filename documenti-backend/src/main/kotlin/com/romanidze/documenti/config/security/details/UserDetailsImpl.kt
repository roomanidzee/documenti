package com.romanidze.documenti.config.security.details

import com.romanidze.documenti.config.security.enums.Role
import com.romanidze.documenti.config.security.enums.UserState
import com.romanidze.documenti.domain.postgres.User

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(private var user: User?): UserDetails {

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

       if(!roleCondition){
           throw IllegalArgumentException("Роли $role не существует")
       }

       if(!stateCondition){
           throw IllegalArgumentException("Состояния $state не существует")
       }

       this.user = User(id = id, username = username, role = role, state = state, password = "")

    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val grantedAuthority = SimpleGrantedAuthority(this.user!!.role)
        return mutableListOf(grantedAuthority)
    }

    override fun isEnabled(): Boolean {
        return UserState.valueOf(this.user!!.state!!) == UserState.CONFIRMED
    }

    override fun getUsername(): String {
        return this.user!!.username!!
    }

    override fun isCredentialsNonExpired(): Boolean {
        return UserState.valueOf(this.user!!.state!!) != UserState.NOT_CONFIRMED
    }

    override fun getPassword(): String {
        return this.user!!.password!!
    }

    override fun isAccountNonExpired(): Boolean {
        return UserState.valueOf(this.user!!.state!!) != UserState.DELETED
    }

    override fun isAccountNonLocked(): Boolean {
        return UserState.valueOf(this.user!!.state!!) != UserState.BANNED
    }
}