package com.jis.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SharedConfiguraion {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public SharedConfiguraion(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    //jobexcution job내에 데이터 공유
    //stepExcution step내에 데이터 공유
    @Bean
    public Job sharedJob() {
        return jobBuilderFactory.get("sharedJob")
                .incrementer(new RunIdIncrementer())
                .start(sharedStep())
                .next(sharedStep2())
                .build();
    }

    @Bean
    public Step sharedStep() {
        return stepBuilderFactory.get("sharedStep")
                .tasklet((contribution, chunkContext) -> {
                    StepExecution stepExecution = contribution.getStepExecution();
                    ExecutionContext executionContext = stepExecution.getExecutionContext();
                    executionContext.putString("stepKey", "step execution context");

                    JobExecution jobExecution = stepExecution.getJobExecution();
                    JobInstance jobInstance = jobExecution.getJobInstance();
                    ExecutionContext jobExecutionContext = jobExecution.getExecutionContext();
                    jobExecutionContext.putString("jobKey", "job execution context");
                    JobParameters jobParameters = jobExecution.getJobParameters();
                    log.info("jobName : {} , stepName : {} , parameter : {}", jobInstance.getJobName(), stepExecution.getStepName(), jobParameters.getLong("run.id"));

                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    public Step sharedStep2() {
        return stepBuilderFactory.get("sharedStep2")
                .tasklet((contribution, chunkContext) -> {
                    StepExecution stepExecution = contribution.getStepExecution();
                    ExecutionContext executionContext = stepExecution.getExecutionContext();

                    JobExecution jobExecution = stepExecution.getJobExecution();
                    ExecutionContext executionContext1 = jobExecution.getExecutionContext();

                    log.info("jobKey : {}, stepKey :{}",
                            executionContext.getString("stepKey", "stepContext"),
                            executionContext1.getString("jobKey", "jobContext"));

                    return RepeatStatus.FINISHED;
                }).build();

    }

}
