package com.romanidze.documenti.components.quartz

import com.romanidze.documenti.config.quartz.QuartzJobLauncher
import com.romanidze.documenti.config.batch.JobBuilder as CustomJobBuilder
import com.romanidze.documenti.dto.jobs.JobRequestDTO

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import org.quartz.Scheduler
import org.quartz.TriggerKey
import org.quartz.CronScheduleBuilder
import org.quartz.CronExpression.isValidExpression
import org.quartz.JobBuilder
import org.quartz.TriggerBuilder
import org.quartz.impl.triggers.CronTriggerImpl

import org.springframework.batch.core.Job

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component


@Component
class QuartzComponent(private val scheduler: Scheduler,
                      private val context: ApplicationContext,
                      private val jobBuilder: CustomJobBuilder){

     companion object{
          val logger: Logger = LogManager.getLogger(QuartzComponent::class)
     }

     private val TRIGGER_PREFIX = "-trigger"
     private val GROUP_NAME = "documenti-group"

     @Synchronized
     fun startJob(jobRequestDTO: JobRequestDTO){

          if(!isValidExpression(jobRequestDTO.cronExpression)){
               throw IllegalArgumentException("На вход пришло неверное cron - выражение: ${jobRequestDTO.cronExpression}")
          }

          logger.debug("Запускаем периодический таск ${jobRequestDTO.jobName} " +
                       "с крон - выражением ${jobRequestDTO.cronExpression}")

          val job = this.context.getBean(jobRequestDTO.jobName, Job::class.java)
          this.jobBuilder.registerJob(job)

          val jobDetail = JobBuilder.newJob(QuartzJobLauncher::class.java)
                                    .withIdentity(jobRequestDTO.jobName, GROUP_NAME)
                                    .usingJobData("jobName", jobRequestDTO.jobName)
                                    .build()

          val trigger = TriggerBuilder.newTrigger()
                                      .withIdentity("${jobRequestDTO.jobName}$TRIGGER_PREFIX", GROUP_NAME)
                                      .withSchedule(CronScheduleBuilder.cronSchedule(jobRequestDTO.cronExpression))
                                      .forJob(jobRequestDTO.jobName, GROUP_NAME)
                                      .build()

          this.scheduler.unscheduleJob(trigger.key)
          this.scheduler.scheduleJob(jobDetail, trigger)

     }

     @Synchronized
     fun restartJob(jobRequestDTO: JobRequestDTO){

          if(!isValidExpression(jobRequestDTO.cronExpression)){
               throw IllegalArgumentException("На вход пришло неверное cron - выражение: ${jobRequestDTO.cronExpression}")
          }

          val key = TriggerKey("${jobRequestDTO.jobName}$TRIGGER_PREFIX", GROUP_NAME)

          val trigger = this.scheduler.getTrigger(key) as CronTriggerImpl
          trigger.cronExpression = jobRequestDTO.cronExpression

          this.scheduler.rescheduleJob(key, trigger)

     }

     @Synchronized
     fun stopJob(jobName: String){

          val key = TriggerKey("$jobName$TRIGGER_PREFIX", GROUP_NAME)
          this.scheduler.unscheduleJob(key)

     }

     @Synchronized
     fun startAllJobs(){

          if(!this.scheduler.isStarted){
               this.scheduler.start()
          }

     }

     @Synchronized
     fun pauseAllJobs(){

          if(this.scheduler.isStarted && !this.scheduler.isInStandbyMode){
               this.scheduler.pauseAll()
          }

     }

     @Synchronized
     fun resumeAllJobs(){

          if(this.scheduler.isStarted && this.scheduler.isInStandbyMode){
               this.scheduler.resumeAll()
          }

     }

     @Synchronized
     fun stopAllJobs(){

          if(this.scheduler.isStarted && !this.scheduler.isShutdown){
               this.scheduler.shutdown()
          }

     }

}