package com.romanidze.documenti.services.interfaces.user

import com.romanidze.documenti.domain.postgres.User
import com.romanidze.documenti.dto.admin.UserAdminDTO

interface UserService {

    fun saveUser(userAdminDTO: UserAdminDTO)
    fun getAllUsers(): List<User>
    fun updateUser(userAdminDTO: UserAdminDTO)
    fun deleteUser(id: Long)

}