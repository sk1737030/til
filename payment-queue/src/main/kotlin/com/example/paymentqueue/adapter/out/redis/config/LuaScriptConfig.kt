package com.example.paymentqueue.adapter.out.redis.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets

/**
 * Lua Script Configuration
 * - Lua 스크립트 파일을 읽어서 Spring Bean으로 등록
 */
@Configuration
class LuaScriptConfig {

  /**
   * 대기열 추가 스크립트
   */
  @Bean
  fun enqueueScript(): RedisScript<List<*>> {
    return RedisScript.of(
      loadScriptAsString("scripts/enqueue.lua"),
      List::class.java  // 반환 타입
    ) as RedisScript<List<*>>
  }

  /**
   * 입장 처리 스크립트
   */
  @Bean
  fun admitUsersScript(): RedisScript<List<*>> {
    return RedisScript.of(
      loadScriptAsString("scripts/admit_users.lua"),
      List::class.java
    )
  }

  /**
   * 순번 조회 스크립트
   */
  @Bean
  fun getPositionScript(): RedisScript<Long> {
    return RedisScript.of(
      loadScriptAsString("scripts/get_position.lua"),
      Long::class.java
    )
  }

  /**
   * 완료 처리 스크립트
   */
  @Bean
  fun completeScript(): RedisScript<Long> {
    return RedisScript.of(
      loadScriptAsString("scripts/complete.lua"),
      Long::class.java
    )
  }

  /**
   * 리소스 파일에서 스크립트 내용을 문자열로 읽기
   */
  private fun loadScriptAsString(path: String): String {
    val resource = ClassPathResource(path)
    return resource.inputStream.use { inputStream ->
      StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8)
    }
  }
}
