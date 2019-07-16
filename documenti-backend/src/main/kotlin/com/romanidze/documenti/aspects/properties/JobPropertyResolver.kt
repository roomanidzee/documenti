package com.romanidze.documenti.aspects.properties

import com.romanidze.documenti.dto.jobs.JobRequestDTO

import org.springframework.core.env.Environment
import org.springframework.core.env.MutablePropertySources
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.PropertySources
import org.springframework.core.env.AbstractEnvironment
import org.springframework.core.env.PropertySourcesPropertyResolver

class JobPropertyResolver(private val jobRequestDTO: JobRequestDTO,
                          env: Environment): PropertySourcesPropertyResolver(propertySources(jobRequestDTO, env)) {

    companion object{
        fun propertySources(jobRequestDTO: JobRequestDTO, env: Environment): PropertySources {

            val propertySources = MutablePropertySources()

            val tempProperties: HashMap<String, Any> = when {
                jobRequestDTO.properties == null -> hashMapOf()
                else -> HashMap(jobRequestDTO.properties)
            }

            propertySources.addFirst(MapPropertySource(jobRequestDTO.jobName, tempProperties))

            (env as AbstractEnvironment).propertySources.forEach { propertySources.addLast(it) }

            return propertySources

        }
    }

    override fun toString(): String {
        return "Параметры для периодического задания ${this.jobRequestDTO.jobName}: ${this.jobRequestDTO.properties}"
    }

}

