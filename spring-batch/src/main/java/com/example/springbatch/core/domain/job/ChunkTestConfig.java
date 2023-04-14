package com.example.springbatch.core.domain.job;

import com.example.springbatch.core.domain.orders.Orders;
import com.example.springbatch.core.domain.orders.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * 목적
 * 청크단위 배치를 확인하기 위해서
 * <p>
 * 총 데이터 1000개
 * <p>
 * 1. readpage 100
 * 2. chunk 10
 * 결과
 * raed 10 번
 * write 100 번 돌것이다
 * <p>
 * --spring.batch.job.names=chunkTestJob
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChunkTestConfig {

  public static final String JOB_NAME = "chunkTestJob";
  public static final String STEP_NAME = "chunkTestStep";
  public static final int PAGE_SIZE = 100;

  private static final int CHUNK_SIZE = 10;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final OrdersRepository repository;

  @Value("${spring.profiles.active}")
  private String profile;

  @Bean(JOB_NAME)
  public Job couponUserMappingContributionMigrationJobV2() {
    return jobBuilderFactory.get(JOB_NAME)
      .incrementer(new RunIdIncrementer())
      .listener(jobExecutionListener())
      .start(couponUserMappingContributionMigrationStepV2())
      .build();
  }

  @Bean(STEP_NAME)
  @JobScope
  public Step couponUserMappingContributionMigrationStepV2() {
    return stepBuilderFactory.get(STEP_NAME)
      .<List<Orders>, List<Orders>>chunk(CHUNK_SIZE)
      .reader(couponUserMappingContributionReaderV2())
      .writer(couponUserMappingContributionWriterV2())
      .listener(getListener())
      .build();
  }

  private static ChunkListener getListener() {
    return new ChunkListener() {
      @Override
      public void beforeChunk(ChunkContext context) {
        StepContext stepContext = context.getStepContext();
        StepExecution stepExecution = stepContext.getStepExecution();

        log.info("###### beforeChunk : " + stepExecution.getReadCount());

      }

      @Override
      public void afterChunk(ChunkContext context) {
        StepContext stepContext = context.getStepContext();
        StepExecution stepExecution = stepContext.getStepExecution();

        log.info("##### afterChunk : " + stepExecution.getCommitCount());

      }

      @Override
      public void afterChunkError(ChunkContext context) {

      }
    };
  }

  /**
   * 무한 루프 왜 안빠지냐
   * <p>
   * pageSize 500
   * pageSize 1000
   * <p>
   * chunk 1000
   * chunk 10
   *
   * @return
   * @modifying 영속성
   */
  @Bean
  @StepScope
  public ItemReader<List<Orders>> couponUserMappingContributionReaderV2() {
    return () -> {
      Specification<Orders> spec = (root, query, criteriaBuilder) -> root.get("price").isNull();

      Page<Orders> contents = repository.findAll(spec, PageRequest.of(0, PAGE_SIZE));

      if (contents.isEmpty()) {
        return null;
      }

      return contents.getContent();
    };
  }

  @StepScope
  @Bean
  public ItemWriter<? super List<Orders>> couponUserMappingContributionWriterV2() {
    return items -> {
      for (List<Orders> item : items) {
        for (Orders orders : item) {
          orders.setPrice(1000);
        }
      }
    };
  }

  private JobExecutionListener jobExecutionListener() {
    return new JobExecutionListener() {
      @Override
      public void beforeJob(JobExecution jobExecution) {
        log.warn("test" + profile, JOB_NAME);
      }

      @Override
      public void afterJob(JobExecution jobExecution) {
        log.warn(profile, JOB_NAME, jobExecution.getId(), jobExecution.getExitStatus().getExitCode());


      }
    };
  }

}
