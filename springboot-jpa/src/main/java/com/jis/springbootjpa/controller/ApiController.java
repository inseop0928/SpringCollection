package com.jis.springbootjpa.controller;


import com.jis.springbootjpa.aop.Auth;
import com.jis.springbootjpa.aop.Decode;
import com.jis.springbootjpa.aop.Timer;
import com.jis.springbootjpa.aop.annotation.MethodAop;
import com.jis.springbootjpa.dto.PostRequestDTo;
import com.jis.springbootjpa.dto.UserDto;
import com.jis.springbootjpa.dto.UserRequest;
import com.jis.springbootjpa.dto.UserResponse;
import com.jis.springbootjpa.sevice.RestTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@Auth
public class ApiController {

    //text
    // content-type에 text/plan이라고 나옴
    @MethodAop("apiTest")
    @GetMapping("/test")
    public String test(@RequestParam String id) {
        return id;
    }

    //json
    @PostMapping("/json")
    public UserDto json(@RequestBody UserDto userDto) {
        return userDto;
    }

    //ResponseEntity 사용하면 리턴상태코드를 지정할수 있다.
    //@Valid 들어오는 값의 validation을 지정할 수 있다.
    @Decode
    @PostMapping("/post/user")
    public ResponseEntity putUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {

        //유효하지 않은 값이 들어오는 경우 @Valid 에러가 바로 발생하는 것이 아니라 bindingResult에 값이 들어옴
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                String message = objectError.getDefaultMessage();

                System.out.println("field: " + fieldError.getField());
                System.out.println(message);

                sb.append("field : " +fieldError.getField());
                sb.append("message : " +message);

            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
        }

        System.out.println(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/post")
    public void post(@RequestBody Map<String, Object> requestData) {

        requestData.forEach((key, value) -> {
            System.out.println("key : " + key);
            System.out.println("value : " + value);
        });

        System.out.println(requestData);
    }

    @GetMapping("/get/{id}")
    public Long get(@PathVariable Long id, @RequestParam(required = false) String name) {
        System.out.println("id : " + id);
        System.out.println("name : " + name);

        return id;
    }

    @PutMapping("/put/{userId}")
    public void put(@RequestBody Map<String, Object> requestData, @PathVariable(name = "userId") Long userId) {
        System.out.println(userId);
    }

    @Timer
    @DeleteMapping("/delete/{userId}")
    public void delete(@RequestParam String account, @PathVariable(name = "userId") Long userId) {
        System.out.println(userId);
    }

    @GetMapping("/exception")
    public void exceptionThrow() throws Exception {
        throw new Exception("에러발생");
    }

    @GetMapping("/getHelloUser/{userId}")
    public UserResponse getHelloUser(@PathVariable int userId, @RequestParam String name,@RequestParam int age) {

        log.info("userId {}",userId);
        UserResponse userResponse = new UserResponse();
        userResponse.setName(name);
        userResponse.setAge(age);
        return userResponse;
    }

    @PostMapping("/postHelloUser")
    public UserResponse postHelloUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = new UserResponse();
        userResponse.setName(userRequest.getName());
        userResponse.setAge(userRequest.getAge());
        return userResponse;
    }
}
