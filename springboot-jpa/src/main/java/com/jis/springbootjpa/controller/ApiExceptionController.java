package com.jis.springbootjpa.controller;


import com.jis.springbootjpa.common.exception.BadRequestException;
import com.jis.springbootjpa.common.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/apiEx")
public class ApiExceptionController {

    //컨트롤러에서 에러가 터지면 우선 exceptionResolver에서 예외 해결을 시도한다.
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e){
        log.error("ex {}",e);
        //로직을 타면 정상상태로 반환 따라서 상단에 에러상태코드 주입
        return new ErrorResult("BAD",e.getMessage());
    }

    //에러 DefaultHandlerExceptionResolver 참고
    @GetMapping("/badRequestEx")
    public String badRequestEx(){
        throw new BadRequestException();
    }

    //404일때 messages.properties에서 메시지를 찾아서 리턴
    @GetMapping("/responseStatusEx")
    public String responseStatusEx(){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"bad_request",new IllegalArgumentException());
    }

    @GetMapping("/illegalArgumentEx")
    public String illegalArgumentEx() {
        throw new IllegalArgumentException("잘못된 사용자");
    }
}
