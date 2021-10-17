package com.jis.springbootjpa.controller;

import com.jis.springbootjpa.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicLong;


@Controller
public class PageController {

    //@Controller로 해야 페이지를 찾을수 있음 @RestController은 안됨
    //String으로 지정하면 자동으로 리소스의 파일을 찾게된다.
    @RequestMapping("/main")
    public String main(){
        return "main.html";
    }

    //@Controller에서 @ResponseBody 사용시 리소스를 찾는게 아니라 json으로 떨어뜨려줌
    //@RestController는 @ResponseBody 필요없음
    @ResponseBody
    @GetMapping("/user")
    public UserDto user(){
        UserDto userDto = new UserDto();
        userDto.setUserName("tester");

        userDto.setUserId(new AtomicLong().get());
        return userDto;
    }
}

