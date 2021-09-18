package com.jis.springbootjpa.controller;

import com.jis.springbootjpa.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController//josn을 반환하는 컨트롤러
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloResponseDto(@RequestParam("name")String name,@RequestParam("amount")int amount){


        return new HelloResponseDto(name,amount);
    }


}
