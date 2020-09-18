package com.example.immoscout24

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan(basePackages = arrayOf("com.example.immoscout24.model"))
class Immoscout24Application

fun main(args: Array<String>) {
    runApplication<Immoscout24Application>(*args)
}
