package com.romanidze.documenti.aspects.classes

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

import org.springframework.batch.core.JobExecution
import org.springframework.stereotype.Component

import java.util.function.Consumer

@Aspect
@Component
class JobExecutionAspect {

    companion object {
        val logger: Logger = LogManager.getLogger(JobExecutionAspect::class)
    }

    private val consumers = LinkedHashSet<Consumer<JobExecution>>()

    fun register(consumer: Consumer<JobExecution>){
        this.consumers.add(consumer)
    }

    @Before("within(org.springframework.batch.core.repository.JobRepository+) " +
            "&& execution(* update(..)) && args(jobExecution)")
    fun jobExecutionUpdated(jobExecution: JobExecution){

        when {
            jobExecution.status.isUnsuccessful ->
                logger.error("${jobExecution.status.name} ${jobExecution.jobInstance.jobName} $jobExecution")
            else -> logger.info("${jobExecution.status.name} ${jobExecution.jobInstance.jobName} $jobExecution")
        }

        this.consumers.forEach { it.accept(jobExecution) }

    }

}