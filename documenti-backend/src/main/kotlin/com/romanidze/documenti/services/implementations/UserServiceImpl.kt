package com.romanidze.documenti.services.implementations

import com.romanidze.documenti.domain.postgres.User
import com.romanidze.documenti.dto.admin.UserAdminDTO
import com.romanidze.documenti.mappers.mapstruct.UserMapper
import com.romanidze.documenti.mappers.mybatis.UserDBMapper
import com.romanidze.documenti.services.interfaces.UserService

import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userMapper: UserMapper,
                      private val userDBMapper: UserDBMapper): UserService {

    override fun saveUser(userAdminDTO: UserAdminDTO) {

        val user: User = this.userMapper.dtoToDomain(userAdminDTO)
        this.userDBMapper.save(user)

    }

    override fun getAllUsers(): List<UserAdminDTO> {

        val users: List<User> = this.userDBMapper.findAll()

        return users.map(this.userMapper::domainToDTO)
                    .toList()

    }

    override fun updateUser(userAdminDTO: UserAdminDTO) {
        val user: User = this.userMapper.dtoToDomain(userAdminDTO)
        this.userDBMapper.update(user)
    }

    override fun deleteUser(id: Long) {
        this.userDBMapper.delete(id)
    }
}