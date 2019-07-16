package com.romanidze.documenti.services.interfaces.admin

import com.romanidze.documenti.dto.jobs.JobRequestDTO
import com.romanidze.documenti.dto.jobs.JobResponseDTO
import org.springframework.security.core.Authentication

interface JobSchedulerService {

    fun startJob(authentication: Authentication?, jobRequestDTO: JobRequestDTO): JobResponseDTO
    fun restartJob(authentication: Authentication?, jobRequestDTO: JobRequestDTO): JobResponseDTO
    fun stopJob(authentication: Authentication?, jobName: String): JobResponseDTO

}