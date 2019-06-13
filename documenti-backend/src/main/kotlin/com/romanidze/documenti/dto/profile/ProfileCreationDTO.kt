package com.romanidze.documenti.dto.profile

import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.Pattern

data class ProfileCreationDTO(
                              @JsonProperty("user_id")
                              var userID: Long,
                              var surname: String,
                              var name: String,
                              var patronymic: String,

                              @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$",
                                       message = "Неверно введён адрес электронной почты")
                              var email: String,

                              @JsonProperty(value = "phone_number")
                              @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$",
                                       message = "Неверно введён номер телефона")
                              var phoneNumber: String)