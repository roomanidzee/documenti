package com.romanidze.documenti.services.implementations.admin

import com.romanidze.documenti.dto.jobs.JobRequestDTO
import com.romanidze.documenti.dto.jobs.JobResponseDTO
import com.romanidze.documenti.services.interfaces.admin.JobSchedulerService

import org.springframework.stereotype.Service

import org.springframework.batch.core.configuration.JobLocator
import org.springframework.batch.core.launch.JobLauncher

import org.springframework.security.core.Authentication

@Service
class JobSchedulerServiceImpl(private val jobLauncher: JobLauncher,
                              private val jobLocator: JobLocator): JobSchedulerService {

    override fun startJob(authentication: Authentication?, jobRequestDTO: JobRequestDTO): JobResponseDTO {

        TODO("Доделать")

    }

    override fun restartJob(authentication: Authentication?, jobRequestDTO: JobRequestDTO): JobResponseDTO {

        //TODO: добавить проверку по Authentication

        TODO("Доделать")

    }

    override fun stopJob(authentication: Authentication?, jobName: String): JobResponseDTO {

        //TODO: добавить проверку по Authentication

        TODO("Доделать")

    }
}