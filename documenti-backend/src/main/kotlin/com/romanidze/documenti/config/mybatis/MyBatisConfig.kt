package com.romanidze.documenti.config.mybatis

import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager

import javax.sql.DataSource

@Configuration
@MapperScan("com.romanidze.documenti.mappers.mybatis")
class MyBatisConfig(private val datasource: DataSource) {

    @Bean
    fun transactionManager(): DataSourceTransactionManager {
        return DataSourceTransactionManager(this.datasource)
    }

    @Bean
    @Throws(Exception::class)
    fun sqlSessionFactory(): SqlSessionFactory {

        val sessionFactory = SqlSessionFactoryBean()
        sessionFactory.setDataSource(this.datasource)

        return sessionFactory.`object`!!

    }

}