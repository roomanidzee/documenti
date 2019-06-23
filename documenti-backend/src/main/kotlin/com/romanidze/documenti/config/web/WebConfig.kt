package com.romanidze.documenti.config.web

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean

import org.springframework.http.converter.StringHttpMessageConverter

import org.springframework.web.multipart.commons.CommonsMultipartResolver

import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer

import java.nio.charset.Charset

@Configuration
@EnableWebMvc
class WebConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {

        registry.addMapping("/api/**")
                .allowedOrigins("*")

    }

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/docs", "/swagger-ui.html")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")

    }

    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer) {
        configurer.enable()
    }

    @Bean
    fun stringHttpMessageConverter(): StringHttpMessageConverter{
        return StringHttpMessageConverter(Charset.forName("UTF-8"))
    }

    @Bean
    fun filterMultipartResolver(): CommonsMultipartResolver{

        val multipartResolver = CommonsMultipartResolver()

        multipartResolver.setMaxUploadSizePerFile(5242880) // 5 МБ
        multipartResolver.setMaxInMemorySize(5242880)
        multipartResolver.setDefaultEncoding("UTF-8")

        return multipartResolver

    }

}