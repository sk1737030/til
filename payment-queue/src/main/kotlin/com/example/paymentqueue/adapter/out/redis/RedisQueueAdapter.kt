package com.example.paymentqueue.adapter.out.redis

import com.example.paymentqueue.application.port.out.QueueManagementPort
import com.example.paymentqueue.config.QueueProperties
import com.example.paymentqueue.domain.QueuePosition
import com.example.paymentqueue.domain.exception.QueueFullException
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.stereotype.Component
import java.util.*

/**
 * Redis 기반 대기열 관리 구현
 */
@Component
class RedisQueueAdapter(
  private val redisTemplate: StringRedisTemplate,
  private val enqueueScript: RedisScript<List<Any>>,
  private val admitUsersScript: RedisScript<List<*>>,
  private val getPositionScript: RedisScript<Long>,
  private val completeScript: RedisScript<Long>,
  private val properties: QueueProperties
) : QueueManagementPort {

  private val log = LoggerFactory.getLogger(javaClass)

  companion object {
    // Redis Key 상수
    const val QUEUE_KEY = "payment:queue:active"
    const val ACTIVE_SET_KEY = "payment:active:users"
    const val ACTIVE_COUNT_KEY = "payment:active:count"
    const val USER_META_KEY_PREFIX = "payment:queue:user:"
  }

  override fun enqueue(userId: String, ipAddress: String?): QueuePosition {
    val keys = listOf(QUEUE_KEY, "$USER_META_KEY_PREFIX$userId")
    val args = listOf(
      userId,
      System.currentTimeMillis().toString(),
      properties.maxSize.toString(),
      ipAddress ?: ""
    )

    // Lua 스크립트 실행
    val result = redisTemplate.execute(enqueueScript, keys, *args.toTypedArray())
      ?: throw RuntimeException("Enqueue script returned null")

    // 결과 파싱
    val success = (result[0] as? Number)?.toLong() ?: 0L

    if (success == 0L) {
      // 실패 - 에러 메시지 추출
      val errorMsg = result[1] as? String ?: "Unknown error"
      throw QueueFullException(errorMsg)
    }

    // 성공 - 순번 추출
    val position = (result[1] as? Number)?.toLong() ?: 0L
    val estimatedWait = position * properties.avgProcessingSeconds

    log.info("User {} enqueued at position {}", userId, position)

    return QueuePosition(position, estimatedWait)
  }

  override fun admitNextUsers(count: Int): List<String> {
    val keys = listOf(QUEUE_KEY, ACTIVE_SET_KEY, ACTIVE_COUNT_KEY)
    val args = listOf(
      properties.maxActive.toString(),
      count.toString()
    )

    @Suppress("UNCHECKED_CAST")
    val result = redisTemplate.execute(admitUsersScript, keys, *args.toTypedArray())
            as? List<String> ?: emptyList()

    if (result.isNotEmpty()) {
      log.info("Admitted {} users: {}", result.size, result)
    }

    return result
  }

  override fun getPosition(userId: String): Optional<QueuePosition> {
    // ZRANK로 순번 조회
    val rank = redisTemplate.opsForZSet().rank(QUEUE_KEY, userId)

    if (rank == null) {
      // 대기열에 없으면 활성 상태인지 확인
      val isActive = redisTemplate.opsForSet().isMember(ACTIVE_SET_KEY, userId) ?: false

      return if (isActive) {
        Optional.of(QueuePosition.admitted())
      } else {
        Optional.empty()
      }
    }

    // 순번 계산 (rank는 0부터 시작)
    val position = rank + 1
    val estimatedWait = position * properties.avgProcessingSeconds

    return Optional.of(QueuePosition(position, estimatedWait))
  }

  override fun isActive(userId: String): Boolean {
    return redisTemplate.opsForSet().isMember(ACTIVE_SET_KEY, userId) ?: false
  }

  override fun getActiveCount(): Long {
    val count = redisTemplate.opsForValue().get(ACTIVE_COUNT_KEY)
    return count?.toLongOrNull() ?: 0L
  }

  override fun completePayment(userId: String) {
    val keys = listOf(ACTIVE_SET_KEY, ACTIVE_COUNT_KEY, "$USER_META_KEY_PREFIX$userId")
    val args = listOf(userId)

    val result = redisTemplate.execute(completeScript, keys, *args.toTypedArray())

    if ((result as? Number)?.toLong() == 1L) {
      log.info("User {} completed payment", userId)
    } else {
      log.warn("User {} was not in active set", userId)
    }
  }

  override fun removeFromQueue(userId: String) {
    // 대기열에서 제거
    redisTemplate.opsForZSet().remove(QUEUE_KEY, userId)

    // 메타데이터 삭제
    redisTemplate.delete("$USER_META_KEY_PREFIX$userId")

    log.info("User {} removed from queue", userId)
  }

}
