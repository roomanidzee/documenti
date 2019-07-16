package com.romanidze.documenti.components.batch

import com.romanidze.documenti.aspects.properties.JobPropertyResolvers
import com.romanidze.documenti.dto.jobs.JobRequestDTO

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.configuration.JobLocator
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.JobOperator

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

import java.util.UUID

@Component
class BatchComponent(private val jobLocator: JobLocator,
                     @Qualifier("customJobLauncher") private val jobLauncher: JobLauncher,
                     @Qualifier("customJobExplorer") private val jobExplorer: JobExplorer,
                     @Qualifier("customJobOperator") private val jobOperator: JobOperator,
                     private val jobPropertyResolvers: JobPropertyResolvers) {

    fun startJob(jobRequestDTO: JobRequestDTO): JobExecution{

        val job = this.jobLocator.getJob(jobRequestDTO.jobName)
        this.jobPropertyResolvers.start(jobRequestDTO)

        val params: HashMap<String, JobParameter>

        when {
            jobRequestDTO.properties == null -> params = hashMapOf()
            else -> {

                params = hashMapOf()

                jobRequestDTO.properties.forEach { (key, value) ->
                    params[key] = JobParameter(value)
                }

            }
        }

        params["uuid"] = JobParameter(UUID.randomUUID().toString())

        val jobParameters = JobParameters(params)

        return this.jobLauncher.run(job, jobParameters)

    }

    fun restartJob(jobRequestDTO: JobRequestDTO){

        val runningJobs = this.jobExplorer.findRunningJobExecutions(jobRequestDTO.jobName)

        runningJobs.forEach{
            this.jobOperator.restart(it.id)
        }

    }

    fun stopJob(jobRequestDTO: JobRequestDTO){

        val runningJobs = this.jobExplorer.findRunningJobExecutions(jobRequestDTO.jobName)

        runningJobs.forEach{
            this.jobOperator.stop(it.id)
        }

    }

}