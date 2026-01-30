package com.example.paymentqueue.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "payment.queue")
data class QueueProperties(
  val maxSize: Long = 10000,
  val maxActive: Long = 1000,
  val admissionRate: Int = 5,
  val admissionIntervalMs: Long = 2000,
  val tokenTtlMinutes: Long = 10,
  val avgProcessingSeconds: Long = 60
)
