package com.romanidze.documenti.services.interfaces

import com.romanidze.documenti.dto.UserDTO

interface UserService {

    fun saveUser(userDTO: UserDTO)
    fun getAllUsers(): List<UserDTO>
    fun updateUser(userDTO: UserDTO)
    fun deleteUser(id: Long)

}