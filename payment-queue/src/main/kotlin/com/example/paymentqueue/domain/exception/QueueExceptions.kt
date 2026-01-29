package com.example.paymentqueue.domain.exception

/**
 * 대기열 관련 예외의 베이스 클래스
 */
sealed class QueueException(message: String) : RuntimeException(message)

/**
 * 대기열이 가득 찼을 때
 */
class QueueFullException(message: String = "대기열이 가득 찼습니다") : QueueException(message)

/**
 * 이미 대기열에 존재할 때
 */
class AlreadyInQueueException(userId: String) : QueueException("사용자 $userId는 이미 대기열에 있습니다")

/**
 * 토큰이 만료되었을 때
 */
class TokenExpiredException(message: String = "토큰이 만료되었습니다") : QueueException(message)

/**
 * 토큰이 유효하지 않을 때
 */
class InvalidTokenException(message: String = "유효하지 않은 토큰입니다") : QueueException(message)

/**
 * 대기열에서 찾을 수 없을 때
 */
class QueueEntryNotFoundException(userId: String) : QueueException("사용자 $userId를 대기열에서 찾을 수 없습니다")
