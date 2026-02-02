package com.example.paymentqueue.adapter.`in`.web.dto

/**
 * 대기열 등록 응답
 */
data class EnqueueResponse(
  val success: Boolean,
  val status: String,
  val position: Long,
  val estimatedWaitSeconds: Long
)
