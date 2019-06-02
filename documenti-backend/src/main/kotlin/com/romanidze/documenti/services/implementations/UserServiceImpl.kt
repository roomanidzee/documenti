package com.romanidze.documenti.services.implementations

import com.romanidze.documenti.domain.User
import com.romanidze.documenti.dto.UserDTO
import com.romanidze.documenti.mappers.mapstruct.UserMapper
import com.romanidze.documenti.mappers.mybatis.UserDBMapper
import com.romanidze.documenti.services.interfaces.UserService

import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userMapper: UserMapper,
                      private val userDBMapper: UserDBMapper): UserService {

    override fun saveUser(userDTO: UserDTO) {

        val user: User = this.userMapper.dtoToDomain(userDTO)
        this.userDBMapper.save(user)

    }

    override fun getAllUsers(): List<UserDTO> {

        val users: List<User> = this.userDBMapper.findAll()

        return users.map(this.userMapper::domainToDTO)
                    .toList()

    }

    override fun updateUser(userDTO: UserDTO) {
        val user: User = this.userMapper.dtoToDomain(userDTO)
        this.userDBMapper.update(user)
    }

    override fun deleteUser(id: Long) {
        this.userDBMapper.delete(id)
    }
}