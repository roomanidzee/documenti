package com.romanidze.documenti.services.implementations.admin

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.stereotype.Service

@Service
class JobListenerServiceImpl: JobExecutionListener {

    companion object{
        val logger: Logger = LogManager.getLogger(JobListenerServiceImpl::class)
    }

    override fun beforeJob(jobExecution: JobExecution) {
        logger.info("Периодическая задача под названием ${jobExecution.jobInstance.jobName} начала выполняться")
    }

    override fun afterJob(jobExecution: JobExecution) {
        logger.info("Результат выполнения задачи под названием ${jobExecution.jobInstance.jobName} " +
                    "с ID = ${jobExecution.jobId}: ${jobExecution.status}")
    }
}