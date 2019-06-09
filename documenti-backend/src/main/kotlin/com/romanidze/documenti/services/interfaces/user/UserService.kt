package com.romanidze.documenti.services.interfaces.user

import com.romanidze.documenti.dto.admin.UserAdminDTO

interface UserService {

    fun saveUser(userAdminDTO: UserAdminDTO)
    fun getAllUsers(): List<UserAdminDTO>
    fun updateUser(userAdminDTO: UserAdminDTO)
    fun deleteUser(id: Long)

}