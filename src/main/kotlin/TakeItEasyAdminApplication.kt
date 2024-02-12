package com.sudosoo.takeItEasyAdmin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
class TakeItEasyAdminApplication

fun main() {
    runApplication<TakeItEasyAdminApplication>()

}
