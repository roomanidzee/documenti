package com.romanidze.documenti.config.quartz

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import org.quartz.JobDataMap
import org.quartz.JobExecutionContext

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.configuration.JobLocator
import org.springframework.batch.core.launch.JobLauncher

import org.springframework.scheduling.quartz.QuartzJobBean

class QuartzJobLauncher: QuartzJobBean() {

    companion object{
        val logger: Logger = LogManager.getLogger(QuartzJobLauncher::class)
    }

    override fun executeInternal(context: JobExecutionContext) {

        val jobDataMap: JobDataMap = context.jobDetail.jobDataMap
        val jobName = jobDataMap.getString("jobName")

        val jobLocator = context.scheduler.context["jobLocator"] as JobLocator
        val jobLauncher = context.scheduler.context["jobLauncher"] as JobLauncher

        val job = jobLocator.getJob(jobName)
        logger.info("Начинаем запускать периодическое задание ${job.name}")

        val jobExecution = jobLauncher.run(job, JobParameters())
        logger.info("Результат выполнения задачи ${job.name} с ID = ${jobExecution.id}: ${jobExecution.status}")

    }
}