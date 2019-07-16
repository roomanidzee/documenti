package com.romanidze.documenti.config.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.JobFactory
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.stereotype.Component

@Component
class JobBuilder(private val jobRegistry: JobRegistry,
                 private val jobFactory: JobBuilderFactory,
                 private val stepFactory: StepBuilderFactory) {

    fun registerJob(job: Job): Job{

        this.jobRegistry.unregister(job.name)

        this.jobRegistry.register(object: JobFactory {

            override fun createJob(): Job {
                return job
            }

            override fun getJobName(): String {
                return job.name
            }

        })

        return job

    }

    fun createJob(name: String, runTask: Runnable): Job{

        val tempStep = this.stepFactory.get("step")
                                       .allowStartIfComplete(true)
                                       .tasklet(RunnableTaskletAdapter(runTask))
                                       .build()

        val tempJob = this.jobFactory.get(name)
                                     .incrementer(RunIdIncrementer())
                                     .start(tempStep)
                                     .build()

        return registerJob(tempJob)

    }

}