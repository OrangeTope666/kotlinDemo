package com.cgs.hb

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import springfox.documentation.swagger2.annotations.EnableSwagger2

// exclude = [DataSourceAutoConfiguration::class, LiquibaseAutoConfiguration::class, HibernateJpaAutoConfiguration::class]
@SpringBootApplication(exclude = [HibernateJpaAutoConfiguration::class])
@EnableSwagger2
@EnableCaching
@EnableScheduling
class DemoApplication

fun main(args: Array<String>) {
	SpringApplication.run(DemoApplication::class.java,*args)
}

