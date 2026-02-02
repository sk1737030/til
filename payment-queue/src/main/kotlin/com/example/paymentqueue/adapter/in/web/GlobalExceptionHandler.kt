package com.example.paymentqueue.adapter.`in`.web

import com.example.paymentqueue.domain.exception.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 전역 예외 처리 핸들러
 */
@RestControllerAdvice
class GlobalExceptionHandler {

  private val log = LoggerFactory.getLogger(javaClass)

  /**
   * Validation 에러 처리
   */
  @ExceptionHandler(MethodArgumentNotValidException::class)
  fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
    val errors = ex.bindingResult.fieldErrors
      .map { "${it.field}: ${it.defaultMessage}" }
      .joinToString(", ")

    log.warn("Validation error: {}", errors)

    return ResponseEntity
      .badRequest()
      .body(ErrorResponse(
        error = "VALIDATION_ERROR",
        message = errors
      ))
  }

  /**
   * 대기열 가득 참
   */
  @ExceptionHandler(QueueFullException::class)
  fun handleQueueFullException(ex: QueueFullException): ResponseEntity<ErrorResponse> {
    log.warn("Queue full: {}", ex.message)

    return ResponseEntity
      .status(HttpStatus.SERVICE_UNAVAILABLE)
      .body(ErrorResponse(
        error = "QUEUE_FULL",
        message = ex.message ?: "대기열이 가득 찼습니다"
      ))
  }

  /**
   * 이미 대기열에 존재
   */
  @ExceptionHandler(AlreadyInQueueException::class)
  fun handleAlreadyInQueueException(ex: AlreadyInQueueException): ResponseEntity<ErrorResponse> {
    log.warn("Already in queue: {}", ex.message)

    return ResponseEntity
      .status(HttpStatus.CONFLICT)
      .body(ErrorResponse(
        error = "ALREADY_IN_QUEUE",
        message = ex.message ?: "이미 대기열에 있습니다"
      ))
  }

  /**
   * 토큰 만료
   */
  @ExceptionHandler(TokenExpiredException::class)
  fun handleTokenExpiredException(ex: TokenExpiredException): ResponseEntity<ErrorResponse> {
    log.warn("Token expired: {}", ex.message)

    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body(ErrorResponse(
        error = "TOKEN_EXPIRED",
        message = ex.message ?: "토큰이 만료되었습니다"
      ))
  }

  /**
   * 잘못된 토큰
   */
  @ExceptionHandler(InvalidTokenException::class)
  fun handleInvalidTokenException(ex: InvalidTokenException): ResponseEntity<ErrorResponse> {
    log.warn("Invalid token: {}", ex.message)

    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body(ErrorResponse(
        error = "INVALID_TOKEN",
        message = ex.message ?: "유효하지 않은 토큰입니다"
      ))
  }

  /**
   * 대기열에 없음
   */
  @ExceptionHandler(QueueEntryNotFoundException::class)
  fun handleQueueEntryNotFoundException(ex: QueueEntryNotFoundException): ResponseEntity<ErrorResponse> {
    log.warn("Queue entry not found: {}", ex.message)

    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(ErrorResponse(
        error = "NOT_FOUND",
        message = ex.message ?: "대기열에서 찾을 수 없습니다"
      ))
  }

  /**
   * 일반 예외
   */
  @ExceptionHandler(Exception::class)
  fun handleGeneralException(ex: Exception): ResponseEntity<ErrorResponse> {
    log.error("Unexpected error", ex)

    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(ErrorResponse(
        error = "INTERNAL_ERROR",
        message = "서버 오류가 발생했습니다"
      ))
  }

  /**
   * 에러 응답 DTO
   */
  data class ErrorResponse(
    val error: String,
    val message: String
  )
}
