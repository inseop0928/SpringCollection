package com.jis.springbootjpa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jis.springbootjpa.dto.BookRequestDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;


public class PageControllerTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void objectMapperTest(){
        ObjectMapper objectMapper =  new ObjectMapper();

        //(String title,String content,String author){
        BookRequestDto bookRequestDto = new BookRequestDto("제목","내용","저자");
        try {

            String text = objectMapper.writeValueAsString(bookRequestDto);
            logger.info(String.format("text : {}"),text);

            BookRequestDto bookRequestDto1  = objectMapper.readValue(text,BookRequestDto.class);

            logger.info(String.format("object : {}"),bookRequestDto1);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}