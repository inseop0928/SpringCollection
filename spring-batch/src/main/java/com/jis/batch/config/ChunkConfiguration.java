package com.jis.batch.config;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
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
    public Job taskletJob(){
        return jobBuilderFactory.get("taskletJob")
                .incrementer(new RunIdIncrementer())
                .start(taskletStep())
                .next(chunkBaseStep(20))
                .build();
    }
    @Bean
    public Step taskletStep(){
        return stepBuilderFactory.get("taskletStep")
                .tasklet(tasklet())
                .build();
    }

    public Tasklet tasklet(){
        List<String> items = getList();
        return (contribution, chunkContext) -> {

            StepExecution stepExecution = contribution.getStepExecution();
            JobParameters jobParameters = stepExecution.getJobParameters();

            String strChunkSize = jobParameters.getString("chunkSize","10");

            int chunkSize = 10;
            if(strChunkSize != null &&  !"".equals(strChunkSize) ){
                chunkSize = Integer.parseInt(strChunkSize);
            }

            int fromIndex = stepExecution.getReadCount();
            int toIndex = fromIndex + chunkSize;

            if(fromIndex >= items.size()){
                return RepeatStatus.FINISHED;
            }
            List<String> subList = items.subList(fromIndex,toIndex);

            log.info("items {} size", subList.size());

            stepExecution.setReadCount(toIndex);

            return RepeatStatus.CONTINUABLE;//계속해서 반복해서 실행
        };
    }

    @Bean
    @JobScope
    public Step chunkBaseStep(@Value("#{jobParameters[chunkSize]}") String chunkSize){

        return stepBuilderFactory.get("chunkBaseStep")
                .<String,String>chunk(10)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    private ItemReader<String> itemReader(){
        return new ListItemReader<>(getList());
    }

    private ItemProcessor<String,String> itemProcessor(){
        return item -> item + ", Spring Batch";
    }

    private ItemWriter<String> itemWriter(){
        return items -> log.info("chunk item size : {}", items.size());
    }

    private List<String> getList(){
        List<String> items = new ArrayList<String>();
        for(int i=0;i<10;i++){
            items.add("hello"+i);
        }
        return items;
    }
}
