package com.example.paymentqueue.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedissonConfig {

  @Value("\${spring.redis.host}")
  private lateinit var redisHost: String

  @Value("\${spring.redis.port}")
  private var redisPort: Int = 6379

  @Bean(destroyMethod = "shutdown")
  fun redissonClient(): RedissonClient {
    val config = Config()
    config.useSingleServer()
      .setAddress("redis://$redisHost:$redisPort")
      .setConnectionPoolSize(20)
      .setConnectionMinimumIdleSize(5)
      .setTimeout(2000)

    return Redisson.create(config)
  }
}
