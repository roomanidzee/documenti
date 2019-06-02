package com.romanidze.documenti.services.interfaces

import com.romanidze.documenti.dto.UserAdminDTO

interface UserService {

    fun saveUser(userAdminDTO: UserAdminDTO)
    fun getAllUsers(): List<UserAdminDTO>
    fun updateUser(userAdminDTO: UserAdminDTO)
    fun deleteUser(id: Long)

}