package com.romanidze.documenti.controllers.admin

import com.romanidze.documenti.dto.utils.MessageResponseDTO
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class JobLauncherController(private val jobLauncher: JobLauncher,
                            @Qualifier("fileCheckingJob")
                            private val job: Job) {

    @GetMapping("/run-file-check")
    fun runFileCheckJob(): ResponseEntity<MessageResponseDTO>{

        val parameters = JobParametersBuilder().addString("source", "admin")
                                               .toJobParameters()

        this.jobLauncher.run(job, parameters)

        return ResponseEntity.ok(MessageResponseDTO(message = "Задача по проверке файлов запущена"))

    }

}