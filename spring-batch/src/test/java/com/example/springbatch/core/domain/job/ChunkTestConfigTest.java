package com.example.springbatch.core.domain.job;

import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

class ChunkTestConfigTest {


  @Test
  void test() {
    LocalDateTime startDate = LocalDateTime.of(2023, 04, 05, 00, 00);
    LocalDateTime expireDate = LocalDateTime.of(2099, 12, 31, 12, 59, 59);

    if (!ObjectUtils.isEmpty(expireDate) && !startDate.isBefore(expireDate)) {
      System.out.printf("asoidjadipja");
    } else {
      System.out.println(startDate + "");
      System.out.println(expireDate + "");

    }
  }

}