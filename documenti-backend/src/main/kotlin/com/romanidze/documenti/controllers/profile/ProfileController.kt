package com.romanidze.documenti.controllers.profile

import com.romanidze.documenti.dto.profile.ProfileCreationDTO
import com.romanidze.documenti.dto.profile.ProfileDTO
import com.romanidze.documenti.services.interfaces.profile.ProfileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/user/profile")
class ProfileController(private val profileService: ProfileService) {

    @PostMapping("/create")
    fun create(@Valid @RequestBody profileDTO: ProfileCreationDTO): ResponseEntity<ProfileDTO>{
        return ResponseEntity.ok(this.profileService.createProfile(profileDTO));
    }

}