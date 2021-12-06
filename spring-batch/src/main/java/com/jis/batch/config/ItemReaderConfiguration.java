package com.jis.batch.config;


import com.jis.batch.model.User;
import com.jis.batch.step.CustomItemReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class ItemReaderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    public ItemReaderConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job itemReaderJob() {
        return this.jobBuilderFactory.get("itemReaderJob")
                .incrementer(new RunIdIncrementer())
                .start(this.itemReaderStep())
                .build();
    }

    @Bean
    public Step itemReaderStep() {
        return this.stepBuilderFactory.get("customItemReader")
                .<User,User>chunk(10)//타입설정
                .reader(new CustomItemReader<>(getUsers()))
                .writer(itemWriter())
                .build();
    }


    private ItemWriter<User> itemWriter() {
        return items -> items.stream().map(User::getName).collect(Collectors.joining(","));
    }


    private List<User> getUsers(){
        List<User> items = new ArrayList<>();

        for(int i =0; i<items.size();i++){
            items.add(new User(i,"name",10,"address"));
        }

        return items;
    }
}
