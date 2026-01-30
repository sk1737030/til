package com.example.paymentqueue.application.port.`in`

/**
 * 결제 완료 Use Case
 */
interface CompletePaymentUseCase {
  /**
   * 결제 완료 처리
   * @param userId 사용자 ID
   * @param token 토큰
   */
  fun complete(userId: String, token: String)
}
