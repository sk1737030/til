package com.example.paymentqueue.application.port.`in`

/**
 * 토큰 검증 Use Case
 */
interface VerifyTokenUseCase {
  /**
   * 입장 토큰 검증
   * @param userId 사용자 ID
   * @param token 토큰
   * @return 토큰 유효성 및 남은 시간
   */
  fun verifyToken(userId: String, token: String): TokenVerificationResult

  data class TokenVerificationResult(
    val valid: Boolean,
    val expiresInSeconds: Long = 0
  )
}
