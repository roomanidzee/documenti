package com.romanidze.documenti.config.quartz

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

import org.springframework.scheduling.quartz.SchedulerFactoryBean

@Configuration
@EnableBatchProcessing
@EnableScheduling
class QuartzSchedulerConfig {

    @Bean
    fun schedulerFactoryBean(): SchedulerFactoryBean{
        return SchedulerFactoryBean()
    }

}