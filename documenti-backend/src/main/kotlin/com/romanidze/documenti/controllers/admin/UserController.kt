package com.romanidze.documenti.controllers.admin

import com.romanidze.documenti.dto.utils.MessageResponseDTO
import com.romanidze.documenti.dto.admin.UserAdminDTO
import com.romanidze.documenti.services.interfaces.user.UserService

import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable

import javax.validation.Valid

@RestController
@RequestMapping("/admin/users")
class UserController(private val userService: UserService) {

    @PostMapping("/create")
    fun createUser(@Valid @RequestBody userAdminDTO: UserAdminDTO): ResponseEntity<MessageResponseDTO>{

        this.userService.saveUser(userAdminDTO)

        return ResponseEntity.ok(MessageResponseDTO(message = "Пользователь сохранён"))

    }

    @GetMapping("/all")
    fun showUsers(): ResponseEntity<List<UserAdminDTO>> {
        return ResponseEntity.ok(this.userService.getAllUsers())
    }

    @PutMapping("/update")
    fun updateUser(@Valid @RequestBody userAdminDTO: UserAdminDTO): ResponseEntity<MessageResponseDTO> {

        this.userService.updateUser(userAdminDTO)

        return ResponseEntity.ok(MessageResponseDTO(message = "Пользователь обновлён"))

    }

    @DeleteMapping("/delete/{id}")
    fun deleteUser(@PathVariable("id") id: Long): ResponseEntity<MessageResponseDTO>{

        this.userService.deleteUser(id)

        return ResponseEntity.ok(MessageResponseDTO(message = "Пользователь с id $id удалён"))

    }

}