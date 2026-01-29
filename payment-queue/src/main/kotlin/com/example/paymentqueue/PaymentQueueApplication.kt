package com.example.paymentqueue

import com.example.paymentqueue.config.QueueProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(QueueProperties::class)
class PaymentQueueApplication

fun main(args: Array<String>) {
    runApplication<PaymentQueueApplication>(*args)
}
