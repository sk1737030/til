package com.example.paymentqueue.adapter.`in`.web

import com.example.paymentqueue.adapter.`in`.web.dto.ApiResponse
import com.example.paymentqueue.adapter.`in`.web.dto.TokenRequest
import com.example.paymentqueue.adapter.`in`.web.dto.TokenVerificationResponse
import com.example.paymentqueue.application.port.`in`.CompletePaymentUseCase
import com.example.paymentqueue.application.port.`in`.VerifyTokenUseCase
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * 결제 관련 REST API Controller
 */
@RestController
@RequestMapping("/api/payment")
@Validated
class PaymentController(
  private val verifyTokenUseCase: VerifyTokenUseCase,
  private val completePaymentUseCase: CompletePaymentUseCase
) {

  private val log = LoggerFactory.getLogger(javaClass)

  /**
   * 토큰 검증 (결제 페이지 진입 시)
   * POST /api/payment/verify-token
   */
  @PostMapping("/verify-token")
  fun verifyToken(@Valid @RequestBody request: TokenRequest): ResponseEntity<TokenVerificationResponse> {
    log.info("Verify token: userId={}", request.userId)

    val result = verifyTokenUseCase.verifyToken(request.userId, request.token)

    val response = TokenVerificationResponse(
      valid = result.valid,
      expiresInSeconds = result.expiresInSeconds
    )

    return ResponseEntity.ok(response)
  }

  /**
   * 결제 완료
   * POST /api/payment/complete
   */
  @PostMapping("/complete")
  fun completePayment(@Valid @RequestBody request: TokenRequest): ResponseEntity<ApiResponse> {
    log.info("Complete payment: userId={}", request.userId)

    completePaymentUseCase.complete(request.userId, request.token)

    return ResponseEntity.ok(ApiResponse(success = true, message = "결제가 완료되었습니다"))
  }
}
