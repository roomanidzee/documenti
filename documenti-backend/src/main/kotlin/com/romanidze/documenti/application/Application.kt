package com.romanidze.documenti.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.romanidze.documenti.config",
                               "com.romanidze.documenti.controllers",
                               "com.romanidze.documenti.mappers",
                               "com.romanidze.documenti.services"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
