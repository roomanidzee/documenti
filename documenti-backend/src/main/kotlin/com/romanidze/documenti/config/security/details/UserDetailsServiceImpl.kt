package com.romanidze.documenti.config.security.details

import com.romanidze.documenti.domain.postgres.User
import com.romanidze.documenti.mappers.mybatis.UserDBMapper

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service("customDetailsService")
class UserDetailsServiceImpl(private val userDBMapper: UserDBMapper): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {

        val user: User =
                this.userDBMapper.findByUsername(username!!) ?:
                   throw IllegalArgumentException("Пользователь с логином $username не найден")

        return UserDetailsImpl(user)

    }

}