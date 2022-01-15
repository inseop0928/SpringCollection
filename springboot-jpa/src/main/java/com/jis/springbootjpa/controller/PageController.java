package com.jis.springbootjpa.controller;

import com.jis.springbootjpa.dto.BookRequestDto;
import com.jis.springbootjpa.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Controller
public class PageController {

    //@Controller로 해야 페이지를 찾을수 있음 @RestController은 안됨
    //String으로 지정하면 자동으로 리소스의 파일을 찾게된다.
    @RequestMapping("/main")
    public String main() {
        return "main.html";
    }

    //@Controller에서 @ResponseBody 사용시 리소스를 찾는게 아니라 json으로 떨어뜨려줌
    //@RestController는 @ResponseBody 필요없음
    @ResponseBody
    @GetMapping("/user")
    public UserDto user() {
        UserDto userDto = new UserDto();
        userDto.setUserName("tester");

        userDto.setUserId(new AtomicLong().get());
        return userDto;
    }

    //bindResult 파라미터는 ModelAttribute 다음에 위치해야하한다.
    @PostMapping("/validate")
    public String validate(@ModelAttribute UserDto userDto, BindingResult bindingResult){

        //BindingResult 스프링에서 제공하는 검증 오류를 보관하는 객체
        //바인딩에러가 발생하면 bindingResult가 있으면 bindingResult에 값을 담아 기존화면으로 리턴한다.
        if(!StringUtils.hasText(userDto.getUserName())){
            bindingResult.addError(new FieldError("userName","userName","이름이 없습니다."));
        }

        if(userDto.getAge() < 0){
            bindingResult.addError(new FieldError("age","age","음수일수 없습니다."));
        }

        if(bindingResult.hasErrors()){
            log.error("errors={}",bindingResult);
            throw new RuntimeException(bindingResult.toString());
        }
        return "index.html";
    }
}

