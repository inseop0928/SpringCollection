package com.jis.springbootjpa.controller;

import com.jis.springbootjpa.dto.HelloResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;


import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)//스프링 부트와 junit 사이에 연결자
@WebMvcTest(controllers = HelloController.class)//컨테이너에 해당 클래스 bean주입
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void helloTest() throws Exception {
        String hello = "hello";

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }


    @Test
    public void dtoTest() throws Exception {
        String name = "test";
        int amount = 1000;

        
        /*
            jsonPath : jsonm 응답값을 필드별로 검증할 수 있는 메소드,$를 기준으로 필드명 명시
            param : api 테스트 시 사용, 값은 String으로만 가능     
         */
        mockMvc.perform(get("/hello/dto").param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //assertThat에 있는 값과 isEqualsTo 의 값을 비교해서 같을 때만 성공
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);

    }


}