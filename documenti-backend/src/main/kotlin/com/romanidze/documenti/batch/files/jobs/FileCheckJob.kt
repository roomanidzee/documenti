package com.romanidze.documenti.batch.files.jobs

import com.romanidze.documenti.batch.files.processors.check.FileCheckProcessor
import com.romanidze.documenti.batch.files.readers.check.FileCheckReader
import com.romanidze.documenti.batch.files.writers.check.FileCheckWriter
import com.romanidze.documenti.config.files.FilesProperties
import com.romanidze.documenti.dto.files.FileValidationDTO

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.listener.JobExecutionListenerSupport

import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class FileCheckJob(private val filesProperties: FilesProperties,
                   private val jobBuilderFactory: JobBuilderFactory,
                   private val stepBuilderFactory: StepBuilderFactory,
                   private val fileCheckProcessor: FileCheckProcessor,
                   private val fileCheckWriter: FileCheckWriter): JobExecutionListenerSupport() {

    companion object{
        val logger: Logger = LogManager.getLogger(FileCheckJob::class)
    }

    private val JOB_NAME = "file-check-job"

    @Bean(name = ["fileCheckingJob"])
    fun fileCheckJob(): Job{

        val step = stepBuilderFactory.get("step-1")
                                     .chunk<FileValidationDTO, FileValidationDTO>(1)
                                     .reader(FileCheckReader(ClassPathResource(filesProperties.validationFilePath)))
                                     .processor(this.fileCheckProcessor)
                                     .writer(this.fileCheckWriter)
                                     .build()

        return jobBuilderFactory.get(JOB_NAME)
                                .incrementer(RunIdIncrementer())
                                .listener(this)
                                .start(step)
                                .build()

    }

    override fun beforeJob(jobExecution: JobExecution) {
        logger.info("Периодическая задача под названием $JOB_NAME начала выполняться")
    }

    override fun afterJob(jobExecution: JobExecution) {
        logger.info("Результат выполнения задачи под названием $JOB_NAME " +
                    "с ID = ${jobExecution.jobId}: ${jobExecution.status}")
    }

}