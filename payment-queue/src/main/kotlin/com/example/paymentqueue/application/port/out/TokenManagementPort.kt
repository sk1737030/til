package com.example.paymentqueue.application.port.out

import com.example.paymentqueue.domain.AdmissionToken
import java.util.*

/**
 * 토큰 관리 Port (Redis 추상화)
 */
interface TokenManagementPort {

  /**
   * 토큰 저장
   */
  fun saveToken(token: AdmissionToken)

  /**
   * 토큰으로 userId 조회
   */
  fun getUserIdByToken(token: String): Optional<String>

  /**
   * 토큰 삭제
   */
  fun deleteToken(token: String)
}
