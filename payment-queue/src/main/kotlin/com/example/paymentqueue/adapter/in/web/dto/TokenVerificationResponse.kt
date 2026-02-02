package com.example.paymentqueue.adapter.`in`.web.dto

/**
 * 토큰 검증 응답
 */
data class TokenVerificationResponse(
  val valid: Boolean,
  val expiresInSeconds: Long
)
