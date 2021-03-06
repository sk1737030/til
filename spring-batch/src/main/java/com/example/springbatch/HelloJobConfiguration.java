package com.example.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class HelloJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("helloJob")
            .start(helloStep())
            .next(helloStep2())
            .build();
    }

    @Bean
    public Step helloStep2() {
        return stepBuilderFactory.get("helloStep2")
            .tasklet((contribution, chunkContext) -> {
                System.out.println(" >> Hello Step2 ");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

    @Bean
    public Step helloStep() {
        return stepBuilderFactory.get("helloStep1")
            .tasklet((contribution, chunkContext) -> {
                System.out.println(" >> Hello Step1 ");
                return RepeatStatus.FINISHED;
            })
            .build();
    }
}
