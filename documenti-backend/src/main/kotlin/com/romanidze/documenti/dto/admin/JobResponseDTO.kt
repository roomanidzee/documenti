package com.romanidze.documenti.dto.admin

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty

import java.time.LocalDateTime

data class JobResponseDTO(@JsonProperty("message")
                          val message: String,

                          @JsonProperty("next_run_time")
                          @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy hh:mm:ss")
                          val nextRunTime: LocalDateTime?)