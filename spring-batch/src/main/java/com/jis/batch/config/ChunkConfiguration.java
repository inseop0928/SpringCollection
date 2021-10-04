package com.jis.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class ChunkConfiguration {

    JobBuilderFactory jobBuilderFactory;
    StepBuilderFactory stepBuilderFactory;

    public ChunkConfiguration(JobBuilderFactory jobBuilderFactory,StepBuilderFactory stepBuilderFactory){
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job chunkJob(){
        return jobBuilderFactory.get("chunkJob")
                .incrementer(new RunIdIncrementer())
                .start(chunkStep())
                .build();
    }
    @Bean
    public Step chunkStep(){
        return stepBuilderFactory.get("chunkStep")
                .tasklet(tasklet())
                .build();
    }

    public Tasklet tasklet(){
        return ((contribution, chunkContext) -> {
            List<String> items = new ArrayList<String>();
            for(int i=0;i<10;i++){
                items.add("hello"+i);
            }
            log.info("items {} size", items.size());

            return RepeatStatus.FINISHED;
        });

    }
}
