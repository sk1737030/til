package com.example.paymentqueue.application.service

import com.example.paymentqueue.application.port.out.QueueLockPort
import com.example.paymentqueue.application.port.out.QueueManagementPort
import com.example.paymentqueue.application.port.out.TokenManagementPort
import com.example.paymentqueue.config.QueueProperties
import com.example.paymentqueue.domain.AdmissionToken
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/**
 * 정기적으로 대기열에서 사용자를 입장시키는 스케줄러
 * - 2초마다 5명씩 입장 처리
 * - Redisson 분산 락으로 다중 인스턴스 환경에서 중복 실행 방지
 */
@Component
class ScheduledAdmissionService(
  private val queuePort: QueueManagementPort,
  private val tokenPort: TokenManagementPort,
  private val lockPort: QueueLockPort,
  private val properties: QueueProperties
) {

  private val log = LoggerFactory.getLogger(javaClass)

  companion object {
    const val SCHEDULER_LOCK_KEY = "payment:scheduler:lock"
  }

  /**
   * 2초마다 실행되는 입장 처리 스케줄러
   */
  @Scheduled(fixedDelayString = "\${payment.queue.admission-interval-ms}")
  fun admitUsers() {
    val lock = lockPort.getLock(SCHEDULER_LOCK_KEY)

    try {
      // 분산 락 획득 시도 (1초 대기)
      if (!lock.tryLock(1, TimeUnit.SECONDS)) {
        log.debug("Another instance is processing admission, skipping...")
        return
      }

      // 락을 획득했으므로 입장 처리 수행
      processAdmission()

    } catch (e: InterruptedException) {
      Thread.currentThread().interrupt()
      log.error("Lock acquisition interrupted", e)
    } finally {
      // 락 해제 (현재 스레드가 보유한 경우에만)
      if (lock.isHeldByCurrentThread) {
        lock.unlock()
        log.debug("Lock released")
      }
    }
  }

  /**
   * 실제 입장 처리 로직
   */
  private fun processAdmission() {
    // 1. 현재 활성 사용자 수 확인
    val activeCount = queuePort.getActiveCount()
    val maxActive = properties.maxActive

    if (activeCount >= maxActive) {
      log.debug("Max active users reached: {}/{}", activeCount, maxActive)
      return
    }

    // 2. 입장 가능 인원 계산
    val canAdmit = minOf(
      properties.admissionRate.toLong(),
      maxActive - activeCount
    ).toInt()

    if (canAdmit <= 0) {
      return
    }

    // 3. 대기열에서 N명 선발
    val admittedUsers = queuePort.admitNextUsers(canAdmit)

    if (admittedUsers.isEmpty()) {
      log.debug("No users in queue to admit")
      return
    }

    // 4. 각 사용자에게 토큰 발급
    admittedUsers.forEach { userId ->
      generateAndStoreToken(userId)
    }

    log.info(
      "✅ Admitted {} users. Active: {}/{}",
      admittedUsers.size,
      activeCount + admittedUsers.size,
      maxActive
    )
  }

  /**
   * 토큰 생성 및 저장
   */
  private fun generateAndStoreToken(userId: String) {
    val token = AdmissionToken.generate(userId, properties.tokenTtlMinutes)
    tokenPort.saveToken(token)

    log.debug("Token generated for user {}: {}", userId, token.token)
  }
}
