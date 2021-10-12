package com.jis.batch.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing //배치 프로세싱 선언
public class SpringBatchApplication {

    public static void main(String[] args) {
        for(String str : args){
            System.out.println("parameters " + str);
        }
        SpringApplication.run(SpringBatchApplication.class, args);
    }

}
