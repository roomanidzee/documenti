package com.romanidze.documenti.config.batch

import org.quartz.Scheduler
import org.quartz.impl.StdSchedulerFactory

import org.springframework.batch.core.configuration.JobLocator
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.JobOperator
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.launch.support.SimpleJobOperator
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean

import org.springframework.beans.factory.annotation.Qualifier

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate

import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor
import org.springframework.transaction.PlatformTransactionManager

import java.util.concurrent.Executors

import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
@EnableAspectJAutoProxy
class BatchConfig(private val jobLocator: JobLocator) {

    private val executorService = Executors.newCachedThreadPool()
    private val DATABASE_TYPE = "POSTGRES"
    private val TABLE_PREFIX = "batch_"

    @Bean
    @Primary
    fun customJobRepository(@Qualifier("dataSource") datasource: DataSource,
                            @Qualifier("transactionManager") transactionManager: PlatformTransactionManager)
                            : JobRepository{

        val factoryBean = JobRepositoryFactoryBean()

        factoryBean.setDatabaseType(this.DATABASE_TYPE)
        factoryBean.setDataSource(datasource)
        factoryBean.setTablePrefix(this.TABLE_PREFIX)
        factoryBean.transactionManager = transactionManager
        factoryBean.afterPropertiesSet()

        return factoryBean.`object`

    }

    @Bean
    @Primary
    fun jobBuilderFactory(@Qualifier("customJobRepository")jobRepository: JobRepository): JobBuilderFactory{
        return JobBuilderFactory(jobRepository)
    }

    @Bean
    @Primary
    fun stepBuilderFactory(@Qualifier("customJobRepository") jobRepository: JobRepository,
                           @Qualifier("transactionManager") transactionManager: PlatformTransactionManager)
                            : StepBuilderFactory{
        return StepBuilderFactory(jobRepository, transactionManager)
    }

    @Bean
    @Primary
    fun customJobLauncher(@Qualifier("customJobRepository") jobRepository: JobRepository): JobLauncher{

        val jobLauncher = SimpleJobLauncher()
        jobLauncher.setJobRepository(jobRepository)
        jobLauncher.setTaskExecutor(ConcurrentTaskExecutor(executorService))

        return jobLauncher

    }

    @Bean
    fun scheduler(@Qualifier("customJobLauncher") jobLauncher: JobLauncher): Scheduler {

        val scheduler = StdSchedulerFactory().scheduler

        scheduler.context["jobLocator"] = this.jobLocator
        scheduler.context["jobLauncher"] = jobLauncher

        return scheduler

    }

    @Bean
    @Primary
    fun customJobExplorer(@Qualifier("dataSource") datasource: DataSource): JobExplorer{

        val factoryBean = JobExplorerFactoryBean()

        factoryBean.setDataSource(datasource)
        factoryBean.setTablePrefix(this.TABLE_PREFIX)
        factoryBean.setJdbcOperations(JdbcTemplate(datasource))
        factoryBean.afterPropertiesSet()

        return factoryBean.`object`

    }

    @Bean
    @Primary
    fun customJobOperator(@Qualifier("customJobLauncher") jobLauncher: JobLauncher,
                          @Qualifier("customJobRepository") jobRepository: JobRepository,
                          @Qualifier("jobRegistry") jobRegistry: JobRegistry,
                          @Qualifier("customJobExplorer") jobExplorer: JobExplorer): JobOperator{

        val jobOperator = SimpleJobOperator()

        jobOperator.setJobLauncher(jobLauncher)
        jobOperator.setJobRepository(jobRepository)
        jobOperator.setJobRegistry(jobRegistry)
        jobOperator.setJobExplorer(jobExplorer)

        return jobOperator

    }

}