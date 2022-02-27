package com.jis.springbootjpa.advice;

import com.jis.springbootjpa.common.exception.UserException;
import com.jis.springbootjpa.common.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//대상을 적용하면 해당 대상(클래스,패키지..)만 적용 아닌경우는 글로벌하게 적용
@RestControllerAdvice(annotations = RestController.class)
public class ExControllerAdvice {

    //컨트롤러에서 에러가 터지면 우선 exceptionResolver에서 예외 해결을 시도한다.
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e){
        log.error("ex {}",e);
        //로직을 타면 정상상태로 반환 따라서 상단에 에러상태코드 주입
        return new ErrorResult("BAD",e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandelr(UserException e){
        log.error("ex",e);
        ErrorResult errorResult = new ErrorResult("USER_EX",e.getMessage());
        return new ResponseEntity(errorResult,HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler//예외를 생략하면 메서드 파라미터가 예외가 지정된다.
    public ErrorResult exHandler(Exception e){
        log.error("exceptionHandler e",e);
        return new ErrorResult("EX","내부오류");
    }
}
