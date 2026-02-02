package com.example.paymentqueue.adapter.`in`.web.dto

/**
 * 대기열 상태 조회 응답
 */
data class QueueStatusResponse(
  val status: String,
  val position: Long?,
  val estimatedWaitSeconds: Long?,
  val admissionToken: String?
)
