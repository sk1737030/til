package com.example.paymentqueue.application.port.out

import org.redisson.api.RLock

/**
 * 분산 락 Port (Redisson 추상화)
 */
interface QueueLockPort {

  /**
   * 분산 락 획득
   * @param lockKey 락 키
   * @return RLock 인스턴스
   */
  fun getLock(lockKey: String): RLock
}
