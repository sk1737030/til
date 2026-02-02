package com.example.paymentqueue.adapter.`in`.web.dto

import javax.validation.constraints.NotBlank

/**
 * 대기열 등록 요청
 */
data class EnqueueRequest(
  @field:NotBlank(message = "userId는 필수입니다")
  val userId: String,

  val ipAddress: String? = null
)
