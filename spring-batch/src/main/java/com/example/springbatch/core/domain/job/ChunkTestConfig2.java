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
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

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
 * --spring.batch.job.names=chunkTestJobV2
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChunkTestConfig2 {

  public static final String JOB_NAME = "chunkTestJobV2";
  public static final String STEP_NAME = "chunkTestStepV2";
  public static final int PAGE_SIZE = 100;

  private static final int CHUNK_SIZE = 100;

  private final EntityManagerFactory entityManagerFactory;

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
      .<Orders, Orders>chunk(CHUNK_SIZE)
      .reader(couponUserMappingContributionReader())
      .writer(couponUserMappingContributionWriter())
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
  public JpaPagingItemReader<Orders> couponUserMappingContributionReader() {
    // TODO: itemreader를 상속받아서
    JpaPagingItemReader<Orders> reader = new JpaPagingItemReader<>() {
      @Override
      public int getPage() {
        return 0;
      }
    };

    reader.setEntityManagerFactory(entityManagerFactory);
    reader.setQueryString("SELECT p FROM orders p WHERE p.price is null");
    reader.setPageSize(CHUNK_SIZE);
    reader.setName("couponUserMappingContributionReaderV2");
    return reader;

  }

  @StepScope
  @Bean
  public ItemWriter<Orders> couponUserMappingContributionWriter() {
    return items -> {
      for (Orders Orders : items) {
        Orders.setPrice(1000);
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
