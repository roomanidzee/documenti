package com.romanidze.documenti.config.quartz

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import org.quartz.JobExecutionContext
import org.springframework.batch.core.JobParameters

import org.springframework.batch.core.configuration.JobLocator
import org.springframework.batch.core.launch.JobLauncher

import org.springframework.scheduling.quartz.QuartzJobBean

data class QuartzJobLauncher(val jobName: String, val jobLauncher: JobLauncher,
                             val jobLocator: JobLocator): QuartzJobBean() {

    companion object{
        val logger: Logger = LogManager.getLogger(QuartzJobLauncher::class)
    }

    override fun executeInternal(context: JobExecutionContext) {

        val job = this.jobLocator.getJob(jobName)
        val jobExecution = this.jobLauncher.run(job, JobParameters())

        logger.info("""
                    | Имя периодической задачи: ${job.name}
                    | ID задачи: ${jobExecution.jobId}
                    | ID запуска задачи: ${jobExecution.id}
                    | Задачу можно ещё раз запустить? ${if (!job.isRestartable) "Нет" else "Да"} 
                    | Статус задачи: ${jobExecution.status}
                    """.trimIndent())

    }
}