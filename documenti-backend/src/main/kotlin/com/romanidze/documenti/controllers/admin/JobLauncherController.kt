package com.romanidze.documenti.controllers.admin

import com.romanidze.documenti.dto.jobs.JobRequestDTO
import com.romanidze.documenti.dto.jobs.JobResponseDTO
import com.romanidze.documenti.services.interfaces.admin.JobSchedulerService

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("/admin/jobs")
class JobLauncherController(private val jobSchedulerService: JobSchedulerService) {

    @PostMapping("/start")
    fun startJob(authentication: Authentication?,
                 @RequestBody jobRequestDTO: JobRequestDTO): ResponseEntity<JobResponseDTO> {
        return ResponseEntity.ok(this.jobSchedulerService.startJob(authentication, jobRequestDTO))
    }

    @PostMapping("/restart")
    fun restartJob(authentication: Authentication?,
                   @RequestBody jobRequestDTO: JobRequestDTO): ResponseEntity<JobResponseDTO>{
        return ResponseEntity.ok(this.jobSchedulerService.restartJob(authentication, jobRequestDTO))
    }

    @GetMapping("/stop/{job_name}")
    fun stopJob(authentication: Authentication?,
                @PathVariable("job_name") jobName: String): ResponseEntity<JobResponseDTO>{
        return ResponseEntity.ok(this.jobSchedulerService.stopJob(authentication, jobName))

    }

}