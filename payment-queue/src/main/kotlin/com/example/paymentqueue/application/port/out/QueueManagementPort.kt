package com.example.paymentqueue.application.port.out

import com.example.paymentqueue.domain.QueuePosition
import java.util.*

/**
 * 대기열 관리 Port (Redis 작업 추상화)
 */
interface QueueManagementPort {

  /**
   * 대기열에 사용자 추가
   */
  fun enqueue(userId: String, ipAddress: String?): QueuePosition

  /**
   * 대기열에서 다음 N명 입장 처리
   * @param count 입장시킬 인원 수
   * @return 입장한 사용자 ID 목록
   */
  fun admitNextUsers(count: Int): List<String>

  /**
   * 사용자의 대기 순번 조회
   * @return Optional - 대기열에 없으면 empty
   */
  fun getPosition(userId: String): Optional<QueuePosition>

  /**
   * 활성 사용자인지 확인
   */
  fun isActive(userId: String): Boolean

  /**
   * 현재 활성 사용자 수 조회
   */
  fun getActiveCount(): Long

  /**
   * 결제 완료 처리 (활성 목록에서 제거)
   */
  fun completePayment(userId: String)

  /**
   * 대기열에서 사용자 제거 (이탈)
   */
  fun removeFromQueue(userId: String)
}
