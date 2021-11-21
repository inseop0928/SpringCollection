package com.jis.springbootjpa.controller;

import com.jis.springbootjpa.dto.UserResponse;
import com.jis.springbootjpa.sevice.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restTemplate")
public class RestTemplateController {

    @Autowired//요즘에는 생성자 주입으로 한다고 함
    private RestTemplateService restTemplateService;

    @GetMapping("/getHello")
    public String getHello(){
        return restTemplateService.getHello();
    }


    @GetMapping("/postHello")
    public UserResponse postHello(){
        return restTemplateService.postHello();
    }
}
