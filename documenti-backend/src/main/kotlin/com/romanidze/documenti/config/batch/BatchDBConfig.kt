package com.romanidze.documenti.config.batch

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.explore.support.SimpleJobExplorer
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean
import org.springframework.batch.support.transaction.ResourcelessTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableBatchProcessing
class BatchDBConfig {

    @Bean
    fun batchTransactionManager(): ResourcelessTransactionManager {
        return ResourcelessTransactionManager()
    }

    @Bean
    fun mapJobRepositoryFactory(batchTransactionManager: ResourcelessTransactionManager): MapJobRepositoryFactoryBean {

        val factory = MapJobRepositoryFactoryBean(batchTransactionManager)
        factory.afterPropertiesSet()

        return factory

    }

    @Bean
    fun batchJobRepository(repositoryFactory: MapJobRepositoryFactoryBean): JobRepository {
        return repositoryFactory.`object`
    }

    @Bean
    fun batchJobExplorer(repositoryFactory: MapJobRepositoryFactoryBean): JobExplorer {

        return SimpleJobExplorer(repositoryFactory.jobInstanceDao,
                                 repositoryFactory.jobExecutionDao,
                                 repositoryFactory.stepExecutionDao,
                                 repositoryFactory.executionContextDao)

    }

    @Bean
    fun batchJobLauncher(jobRepository: JobRepository): SimpleJobLauncher {

        val launcher = SimpleJobLauncher()
        launcher.setJobRepository(jobRepository)

        return launcher

    }

}