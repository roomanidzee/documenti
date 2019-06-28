package com.romanidze.documenti.config.quartz

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import org.springframework.scheduling.quartz.SchedulerFactoryBean

@Configuration
class QuartzSchedulerConfig {

    @Bean
    fun schedulerFactoryBean(): SchedulerFactoryBean{
        return SchedulerFactoryBean()
    }

}