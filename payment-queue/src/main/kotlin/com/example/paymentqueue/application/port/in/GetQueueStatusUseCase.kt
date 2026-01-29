package com.example.paymentqueue.application.port.`in`

import com.example.paymentqueue.domain.QueuePosition
import com.example.paymentqueue.domain.QueueStatus

/**
 * 대기열 상태 조회 Use Case
 */
interface GetQueueStatusUseCase {
    /**
     * 사용자의 대기열 상태 조회
     * @param userId 사용자 ID
     * @return 상태와 순번 정보
     */
    fun getStatus(userId: String): QueueStatusResult

    /**
     * 대기열 상태 조회 결과
     * - 인터페이스 내부에 중첩 클래스로 정의
     */
    data class QueueStatusResult(
        val status: QueueStatus,
        val position: QueuePosition?,
        val admissionToken: String? = null
    )
}
