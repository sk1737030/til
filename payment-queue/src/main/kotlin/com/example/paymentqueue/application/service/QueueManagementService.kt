package com.example.paymentqueue.application.service

import com.example.paymentqueue.application.port.`in`.CompletePaymentUseCase
import com.example.paymentqueue.application.port.`in`.EnqueueUseCase
import com.example.paymentqueue.application.port.`in`.GetQueueStatusUseCase
import com.example.paymentqueue.application.port.`in`.VerifyTokenUseCase
import com.example.paymentqueue.application.port.out.QueueManagementPort
import com.example.paymentqueue.application.port.out.TokenManagementPort
import com.example.paymentqueue.domain.QueuePosition
import com.example.paymentqueue.domain.QueueStatus
import com.example.paymentqueue.domain.exception.InvalidTokenException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * 대기열 관리 서비스
 * - 모든 Use Case 구현
 */
@Service
class QueueManagementService(
  private val queuePort: QueueManagementPort,
  private val tokenPort: TokenManagementPort
) : EnqueueUseCase,
  GetQueueStatusUseCase,
  VerifyTokenUseCase,
  CompletePaymentUseCase {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun enqueue(userId: String, ipAddress: String?): QueuePosition {
    log.info("Enqueueing user: {}", userId)
    return queuePort.enqueue(userId, ipAddress)
  }

  override fun getStatus(userId: String): GetQueueStatusUseCase.QueueStatusResult {
    // 1. 활성 상태인지 확인
    if (queuePort.isActive(userId)) {
      // 토큰이 있는지 확인 (간단하게 ADMITTED로 반환)
      return GetQueueStatusUseCase.QueueStatusResult(
        status = QueueStatus.ADMITTED,
        position = QueuePosition.admitted(),
        admissionToken = null  // 실제로는 저장된 토큰을 반환해야 하지만 여기서는 생략
      )
    }

    // 2. 대기열에 있는지 확인
    val positionOpt = queuePort.getPosition(userId)

    return if (positionOpt.isPresent) {
      GetQueueStatusUseCase.QueueStatusResult(
        status = QueueStatus.WAITING,
        position = positionOpt.get(),
        admissionToken = null
      )
    } else {
      // 3. 대기열에도 없음
      GetQueueStatusUseCase.QueueStatusResult(
        status = QueueStatus.NOT_FOUND,
        position = null,
        admissionToken = null
      )
    }
  }

  override fun verifyToken(userId: String, token: String): VerifyTokenUseCase.TokenVerificationResult {
    // 토큰으로 userId 조회
    val storedUserIdOpt = tokenPort.getUserIdByToken(token)

    if (storedUserIdOpt.isEmpty) {
      // 토큰이 없음 (만료되었거나 존재하지 않음)
      return VerifyTokenUseCase.TokenVerificationResult(
        valid = false,
        expiresInSeconds = 0
      )
    }

    val storedUserId = storedUserIdOpt.get()

    if (storedUserId != userId) {
      // userId 불일치
      log.warn("Token userId mismatch. Expected: {}, Actual: {}", userId, storedUserId)
      return VerifyTokenUseCase.TokenVerificationResult(
        valid = false,
        expiresInSeconds = 0
      )
    }

    // 토큰 유효 (만료 시간은 단순화를 위해 0으로 설정)
    return VerifyTokenUseCase.TokenVerificationResult(
      valid = true,
      expiresInSeconds = 0  // 실제로는 Redis TTL을 조회해야 함
    )
  }

  override fun complete(userId: String, token: String) {
    // 1. 토큰 검증
    val verification = verifyToken(userId, token)
    if (!verification.valid) {
      throw InvalidTokenException("유효하지 않은 토큰입니다")
    }

    // 2. 결제 완료 처리
    queuePort.completePayment(userId)

    // 3. 토큰 삭제
    tokenPort.deleteToken(token)

    log.info("User {} completed payment", userId)
  }
}
