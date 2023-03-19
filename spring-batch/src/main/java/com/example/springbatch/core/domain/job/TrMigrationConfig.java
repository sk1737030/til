package com.example.springbatch.core.domain.job;

import com.example.springbatch.core.domain.accounts.Accounts;
import com.example.springbatch.core.domain.accounts.AccountsRepository;
import com.example.springbatch.core.domain.orders.Orders;
import com.example.springbatch.core.domain.orders.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;

/**
 * desc: 주문 테이블 -> 정산 테이블 이관
 * run: --spring.batch.job.names=trMigrationJob
 */
@Configuration
@RequiredArgsConstructor
public class TrMigrationConfig {

  private final AccountsRepository accountsRepository;
  private final OrdersRepository ordersRepository;
  private final StepBuilderFactory stepBuilderFactory;
  private final JobBuilderFactory jobBuilderFactory;

  @Bean
  public Job trMigrationJob(Step trMigrationStep) {
    return jobBuilderFactory.get("trMigrationJob")
        .incrementer(new RunIdIncrementer())
        .start(trMigrationStep)
        .build();
  }

  @JobScope
  @Bean
  public Step trMigrationStep(ItemReader<Orders> trOrdersReader,
                              ItemProcessor<Orders, Accounts> toOrderProcessor,
                              ItemWriter<Accounts> trOrdersWriter) {
    return stepBuilderFactory.get("trMigrationStep")
        .<Orders, Accounts>chunk(5)
        .reader(trOrdersReader)
        .processor(toOrderProcessor)
        .writer(trOrdersWriter)
        .build();
  }

//  @StepScope
//  @Bean
//  public RepositoryItemWriter<Accounts> trOrdersWriter() {
//    return new RepositoryItemWriterBuilder<Accounts>()
//        .repository(accountsRepository)
//        .methodName("save")
//        .build();
//
//  }

  @StepScope
  @Bean
  public ItemWriter<Accounts> trOrdersWriter() {
    return items -> items.forEach(accountsRepository::save);
  }

  @StepScope
  @Bean
  public ItemProcessor<Orders, Accounts> toOrderProcessor() {
    return item -> new Accounts(item);
  }

  @StepScope
  @Bean
  public RepositoryItemReader<Orders> trOrdersReader() {
    return new RepositoryItemReaderBuilder<Orders>()
        .name("trOrdersReader")
        .repository(ordersRepository)
        .methodName("findAll")
        .pageSize(5)
        .arguments(Arrays.asList())
        .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
        .build();
  }

}
