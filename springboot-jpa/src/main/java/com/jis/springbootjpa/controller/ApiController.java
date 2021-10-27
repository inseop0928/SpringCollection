package com.jis.springbootjpa.controller;


import com.jis.springbootjpa.aop.Decode;
import com.jis.springbootjpa.aop.Timer;
import com.jis.springbootjpa.dto.PostRequestDTo;
import com.jis.springbootjpa.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    //text
    // content-type에 text/plan이라고 나옴
    @GetMapping("/test")
    public String text(@RequestParam String id) {
        return id;
    }

    //json
    @PostMapping("/json")
    public UserDto json(@RequestBody UserDto userDto) {
        return userDto;
    }


    //ResponseEntity 사용하면 리턴상태코드를 지정할수 있다.
    @Decode
    @PutMapping("/put/user")
    public ResponseEntity<UserDto> putUser(@RequestBody UserDto userDto) {
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
    public Long get(@PathVariable Long id, @RequestParam String name) {
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
}
