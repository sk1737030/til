package com.example.paymentqueue.adapter.`in`.web

import com.example.paymentqueue.adapter.`in`.web.dto.ApiResponse
import com.example.paymentqueue.adapter.`in`.web.dto.EnqueueRequest
import com.example.paymentqueue.adapter.`in`.web.dto.EnqueueResponse
import com.example.paymentqueue.adapter.`in`.web.dto.QueueStatusResponse
import com.example.paymentqueue.application.port.`in`.EnqueueUseCase
import com.example.paymentqueue.application.port.`in`.GetQueueStatusUseCase
import com.example.paymentqueue.application.port.out.QueueManagementPort
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * 대기열 관리 REST API Controller
 */
@RestController
@RequestMapping("/api/queue")
@Validated
class QueueController(
  private val enqueueUseCase: EnqueueUseCase,
  private val getQueueStatusUseCase: GetQueueStatusUseCase,
  private val queuePort: QueueManagementPort
) {

  private val log = LoggerFactory.getLogger(javaClass)

  /**
   * 대기열 등록
   * POST /api/queue/enqueue
   */
  @PostMapping("/enqueue")
  fun enqueue(@Valid @RequestBody request: EnqueueRequest): ResponseEntity<EnqueueResponse> {
    log.info("Enqueue request: userId={}", request.userId)

    val position = enqueueUseCase.enqueue(request.userId, request.ipAddress)

    val response = EnqueueResponse(
      success = true,
      status = "QUEUED",
      position = position.position,
      estimatedWaitSeconds = position.estimatedWaitSeconds
    )

    return ResponseEntity.ok(response)
  }

  /**
   * 대기열 상태 조회 (Polling)
   * GET /api/queue/status/{userId}
   */
  @GetMapping("/status/{userId}")
  fun getStatus(@PathVariable userId: String): ResponseEntity<QueueStatusResponse> {
    log.debug("Status check: userId={}", userId)

    val statusResult = getQueueStatusUseCase.getStatus(userId)

    val response = QueueStatusResponse(
      status = statusResult.status.name,
      position = statusResult.position?.position,
      estimatedWaitSeconds = statusResult.position?.estimatedWaitSeconds,
      admissionToken = statusResult.admissionToken
    )

    return ResponseEntity.ok(response)
  }

  /**
   * 대기열에서 이탈
   * DELETE /api/queue/leave/{userId}
   */
  @DeleteMapping("/leave/{userId}")
  fun leaveQueue(@PathVariable userId: String): ResponseEntity<ApiResponse> {
    log.info("Leave queue: userId={}", userId)

    queuePort.removeFromQueue(userId)

    return ResponseEntity.ok(ApiResponse(success = true, message = "대기열에서 제거되었습니다"))
  }
}
