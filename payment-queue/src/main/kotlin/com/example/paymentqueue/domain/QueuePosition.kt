package com.example.paymentqueue.domain

/**
 * 대기 순번 및 예상 대기시간 - 불변 Value Object
 */
data class QueuePosition(
  val position: Long,
  val estimatedWaitSeconds: Long
) {
  init {
    require(position >= 0) { "position은 0 이상이어야 합니다" }
    require(estimatedWaitSeconds >= 0) { "estimatedWaitSeconds는 0 이상이어야 합니다" }
  }

  companion object {
    /**
     * 입장이 허가된 상태 (순번 0, 대기시간 0)
     */
    fun admitted(): QueuePosition {
      return QueuePosition(position = 0, estimatedWaitSeconds = 0)
    }

    /**
     * 순번과 평균 처리 시간으로 대기 시간 계산
     */
    fun from(position: Long, avgProcessingSeconds: Long): QueuePosition {
      val estimatedWait = position * avgProcessingSeconds
      return QueuePosition(position, estimatedWait)
    }
  }
}
