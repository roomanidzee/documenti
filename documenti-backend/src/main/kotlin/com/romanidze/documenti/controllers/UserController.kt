package com.romanidze.documenti.controllers

import com.romanidze.documenti.dto.MessageResponseDTO
import com.romanidze.documenti.dto.UserDTO
import com.romanidze.documenti.services.interfaces.UserService

import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping("/create")
    fun createUser(@RequestBody userDTO: UserDTO): ResponseEntity<MessageResponseDTO>{

        this.userService.saveUser(userDTO)

        return ResponseEntity.ok(MessageResponseDTO(message="Пользователь сохранён"))

    }

    @GetMapping("/all")
    fun showUsers(): ResponseEntity<List<UserDTO>> {
        return ResponseEntity.ok(this.userService.getAllUsers())
    }

    @PutMapping("/update")
    fun updateUser(@RequestBody userDTO: UserDTO): ResponseEntity<MessageResponseDTO> {

        this.userService.updateUser(userDTO)

        return ResponseEntity.ok(MessageResponseDTO(message="Пользователь обновлён"))

    }

    @DeleteMapping("/delete/{id}")
    fun deleteUser(@PathVariable("id") id: Long): ResponseEntity<MessageResponseDTO>{

        this.userService.deleteUser(id)

        return ResponseEntity.ok(MessageResponseDTO(message="Пользователь с id $id удалён"))

    }

}