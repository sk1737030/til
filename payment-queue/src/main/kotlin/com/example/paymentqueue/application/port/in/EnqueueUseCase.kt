package com.example.paymentqueue.application.port.`in`

import com.example.paymentqueue.domain.QueuePosition

/**
 * 대기열 등록 Use Case
 */
interface EnqueueUseCase {
  /**
   * 사용자를 대기열에 추가
   * @param userId 사용자 ID
   * @param ipAddress IP 주소 (optional)
   * @return 대기 순번 정보
   */
  fun enqueue(userId: String, ipAddress: String? = null): QueuePosition
}
