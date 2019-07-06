package com.romanidze.documenti.services.implementations.admin

import com.romanidze.documenti.config.quartz.QuartzJobLauncher
import com.romanidze.documenti.dto.admin.JobRequestDTO
import com.romanidze.documenti.dto.admin.JobResponseDTO
import com.romanidze.documenti.services.interfaces.admin.JobSchedulerService

import org.springframework.stereotype.Service

import org.springframework.batch.core.configuration.JobLocator
import org.springframework.batch.core.launch.JobLauncher

import org.quartz.CronExpression
import org.quartz.CronExpression.isValidExpression
import org.quartz.JobDataMap
import org.quartz.JobKey
import org.quartz.TriggerKey
import org.quartz.Scheduler
import org.quartz.impl.JobDetailImpl
import org.quartz.impl.triggers.CronTriggerImpl

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.Authentication

import java.sql.Timestamp
import java.util.Date

@Service
class JobSchedulerServiceImpl(@Qualifier("schedulerFactoryBean")
                              private val scheduler: Scheduler,
                              private val jobLauncher: JobLauncher,
                              private val jobLocator: JobLocator): JobSchedulerService {

    override fun startJob(authentication: Authentication?, jobRequestDTO: JobRequestDTO): JobResponseDTO {

        //TODO: добавить проверку по Authentication

        if(!isValidExpression(jobRequestDTO.cronExpression)){
            throw IllegalArgumentException("На вход пришло неверное cron - выражение: ${jobRequestDTO.cronExpression}")
        }

        val jobKey = JobKey(jobRequestDTO.jobName)
        val jobDetail = this.scheduler.getJobDetail(jobKey)

        val jobDetailImpl = JobDetailImpl()

        if(jobDetail == null){

            jobDetailImpl.name = jobRequestDTO.jobName
            jobDetailImpl.jobClass = QuartzJobLauncher::class.java

            val jobDataMap = JobDataMap()
            jobDataMap["jobName"] = jobRequestDTO.jobName
            jobDataMap["jobLauncher"] = this.jobLauncher
            jobDataMap["jobLocator"] = this.jobLocator

            jobDetailImpl.jobDataMap = jobDataMap

        }

        val trigger = CronTriggerImpl()

        trigger.name = "${jobRequestDTO.jobName}_Trigger"
        trigger.jobKey = jobKey
        trigger.cronExpression = jobRequestDTO.cronExpression

        if(jobDetail != null){
            this.scheduler.scheduleJob(trigger)
        }else{
            this.scheduler.scheduleJob(jobDetailImpl, trigger)
        }

        val cronExpression = CronExpression(jobRequestDTO.cronExpression)
        val nextTime = Timestamp(cronExpression.getNextValidTimeAfter(Date()).time).toLocalDateTime()

        return JobResponseDTO(message="Периодическая задача ${jobRequestDTO.jobName} была запущена",
                              nextRunTime = nextTime)

    }

    override fun restartJob(authentication: Authentication?, jobRequestDTO: JobRequestDTO): JobResponseDTO {

        //TODO: добавить проверку по Authentication

        if(!isValidExpression(jobRequestDTO.cronExpression)){
            throw IllegalArgumentException("На вход пришло неверное cron - выражение: ${jobRequestDTO.cronExpression}")
        }

        val key = TriggerKey("${jobRequestDTO.jobName}_Trigger")

        val trigger = this.scheduler.getTrigger(key) as CronTriggerImpl
        trigger.cronExpression = jobRequestDTO.cronExpression

        this.scheduler.rescheduleJob(key, trigger)

        val cronExpression = CronExpression(jobRequestDTO.cronExpression)
        val nextTime = Timestamp(cronExpression.getNextValidTimeAfter(Date()).time).toLocalDateTime()

        return JobResponseDTO(message="Периодическая задача ${jobRequestDTO.jobName} была перезапущена",
                              nextRunTime = nextTime)

    }

    override fun stopJob(authentication: Authentication?, jobName: String): JobResponseDTO {

        //TODO: добавить проверку по Authentication

        val key = TriggerKey("${jobName}_Trigger")

        this.scheduler.unscheduleJob(key)

        return JobResponseDTO(message="Периодическая задача $jobName была перезапущена",
                              nextRunTime = null)

    }
}