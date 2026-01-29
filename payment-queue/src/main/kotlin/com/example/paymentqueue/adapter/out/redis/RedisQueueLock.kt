package com.example.paymentqueue.adapter.out.redis

import com.example.paymentqueue.application.port.out.QueueLockPort
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

/**
 * Redisson 기반 분산 락 구현
 */
@Component
class RedisQueueLock(
  private val redissonClient: RedissonClient
) : QueueLockPort {

  override fun getLock(lockKey: String): RLock {
    return redissonClient.getLock(lockKey)
  }

}
