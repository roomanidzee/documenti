package com.romanidze.documenti.aspects.properties

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import com.romanidze.documenti.aspects.classes.JobExecutionAspect
import com.romanidze.documenti.dto.jobs.JobRequestDTO

import org.springframework.batch.core.JobExecution
import org.springframework.core.env.Environment
import org.springframework.core.env.PropertyResolver
import org.springframework.stereotype.Component

import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer

@Component
class JobPropertyResolvers(private val env: Environment,
                           private val jobExecutionAspect: JobExecutionAspect): Consumer<JobExecution> {

    companion object{
        lateinit var JobProperties: JobPropertyResolvers
        val logger: Logger = LogManager.getLogger(JobPropertyResolvers::class)
    }

    private val resolvers = ConcurrentHashMap<String, JobPropertyResolver>()

    init {
        this.jobExecutionAspect.register(this)
        JobProperties = this
    }

    fun of(jobName: String): PropertyResolver{
        return resolvers[jobName] ?: this.env
    }

    fun start(jobRequestDTO: JobRequestDTO){

        val resolver = JobPropertyResolver(jobRequestDTO, this.env)
        this.resolvers[jobRequestDTO.jobName] = resolver

        logger.info("Включён $resolver")

    }

    override fun accept(t: JobExecution) {

        if(!t.isRunning){
            val resolver = this.resolvers.remove(t.jobInstance.jobName)

            if(resolver != null){
                logger.info("Отключён $resolver")
            }

        }

    }
}