package com.example.paymentqueue.domain

import java.time.Instant

/**
 * 대기열 엔트리 - 불변 Value Object
 */
data class QueueEntry(
    val userId: String,
    val enqueueTime: Instant,
    val ipAddress: String? = null,
    val status: QueueStatus = QueueStatus.WAITING
) {
    init {
        require(userId.isNotBlank()) { "userId는 비어있을 수 없습니다" }
    }

    companion object {
        fun create(userId: String, ipAddress: String? = null): QueueEntry {
            return QueueEntry(
                userId = userId,
                enqueueTime = Instant.now(),
                ipAddress = ipAddress
            )
        }
    }
}
