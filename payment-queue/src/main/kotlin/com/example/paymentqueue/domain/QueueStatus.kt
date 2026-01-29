package com.example.paymentqueue.domain

enum class QueueStatus {

  WAITING,      // 대기열에서 대기 중
  ADMITTED,     // 입장 허가됨
  PROCESSING,   // 결제 진행 중
  COMPLETED,    // 결제 완료
  NOT_FOUND     // 대기열에 없음

}
