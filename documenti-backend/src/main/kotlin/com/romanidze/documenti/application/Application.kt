package com.romanidze.documenti.application

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@ComponentScan(basePackages = ["com.romanidze.documenti.batch",
                               "com.romanidze.documenti.components",
                               "com.romanidze.documenti.config",
                               "com.romanidze.documenti.controllers",
                               "com.romanidze.documenti.mappers",
                               "com.romanidze.documenti.services"])
@EnableBatchProcessing
@EnableScheduling
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
