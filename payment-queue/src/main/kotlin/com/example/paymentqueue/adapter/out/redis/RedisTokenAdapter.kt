package com.example.paymentqueue.adapter.out.redis

import com.example.paymentqueue.application.port.out.TokenManagementPort
import com.example.paymentqueue.config.QueueProperties
import com.example.paymentqueue.domain.AdmissionToken
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

/**
 * Redis 기반 토큰 관리 구현
 */
@Component
class RedisTokenAdapter(
  private val redisTemplate: StringRedisTemplate,
  private val properties: QueueProperties
) : TokenManagementPort {

  companion object {
    const val TOKEN_KEY_PREFIX = "payment:token:"
  }

  override fun saveToken(token: AdmissionToken) {
    val key = "$TOKEN_KEY_PREFIX${token.token}"
    val ttl = Duration.ofMinutes(properties.tokenTtlMinutes)

    // TTL과 함께 저장
    redisTemplate.opsForValue().set(key, token.userId, ttl)
  }

  override fun getUserIdByToken(token: String): Optional<String> {
    val key = "$TOKEN_KEY_PREFIX$token"
    val userId = redisTemplate.opsForValue().get(key)

    return Optional.ofNullable(userId)
  }

  override fun deleteToken(token: String) {
    val key = "$TOKEN_KEY_PREFIX$token"
    redisTemplate.delete(key)
  }
}
