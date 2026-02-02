package com.example.paymentqueue.adapter.`in`.web.dto

import javax.validation.constraints.NotBlank

/**
 * 토큰 검증 및 결제 완료 요청
 */
data class TokenRequest(
  @field:NotBlank(message = "userId는 필수입니다")
  val userId: String,

  @field:NotBlank(message = "token은 필수입니다")
  val token: String
)
