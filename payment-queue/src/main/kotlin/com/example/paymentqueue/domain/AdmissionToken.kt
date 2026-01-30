package com.example.paymentqueue.domain

import java.time.Instant
import java.util.*

/**
 * 입장 토큰 - 불변 Value Object
 */
data class AdmissionToken(
  val token: String,
  val userId: String,
  val issuedAt: Instant,
  val expiresAt: Instant
) {
  init {
    require(token.isNotBlank()) { "token은 비어있을 수 없습니다" }
    require(userId.isNotBlank()) { "userId는 비어있을 수 없습니다" }
    require(expiresAt.isAfter(issuedAt)) { "expiresAt은 issuedAt보다 이후여야 합니다" }
  }

  fun isValid(): Boolean {
    return Instant.now().isBefore(expiresAt)
  }

  fun expiresInSeconds(): Long {
    val now = Instant.now()
    return if (now.isBefore(expiresAt)) {
      expiresAt.epochSecond - now.epochSecond
    } else {
      0
    }
  }

  companion object {
    /**
     * 새로운 토큰 생성
     */
    fun generate(userId: String, ttlMinutes: Long): AdmissionToken {
      val now = Instant.now()
      return AdmissionToken(
        token = UUID.randomUUID().toString(),
        userId = userId,
        issuedAt = now,
        expiresAt = now.plusSeconds(ttlMinutes * 60)
      )
    }
  }
}
