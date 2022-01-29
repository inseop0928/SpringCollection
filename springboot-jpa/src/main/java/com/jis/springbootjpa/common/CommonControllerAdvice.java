package com.jis.springbootjpa.common;


import com.jis.springbootjpa.dto.ErrorResponseDto;
import com.jis.springbootjpa.dto.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.w3c.dom.Node;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


//경로 설정 및 생략가능
@RestControllerAdvice(basePackages = "com.jis.springbootjpa.controller")
public class CommonControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e){

        //추후 로그저장 구현
        System.out.println("--------------exceptionHandler-----------");
        System.out.println(e.getClass().getName());
        System.out.println(e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest){

        List<Error> errorList = new ArrayList<Error>();

        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors().forEach(objectError -> {
            FieldError fieldError = (FieldError) objectError;

            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            String value = fieldError.getRejectedValue().toString();

            Error error = new Error();
            error.setField(fieldName);
            error.setMessage(message);
            error.setInvalidValue(value);

            errorList.add(error);

        });

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponseDto.setResultCode("Fail");
        errorResponseDto.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponseDto.setErrors(errorList);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity missingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest httpServletRequest){

        String fieldName = e.getParameterName();
        String fieldType = e.getParameterType();
        String invalidvalue = e.getMessage();

        System.out.println(fieldName);
        System.out.println(fieldType);
        System.out.println(invalidvalue);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(ConstraintViolationException e,HttpServletRequest httpServletRequest){

        List<Error> errorList = new ArrayList<Error>();
        e.getConstraintViolations().forEach(error->{
            Stream<Path.Node> stream = StreamSupport.stream(error.getPropertyPath().spliterator(),false);
            List<Path.Node> list = stream.collect(Collectors.toList());

            String fieldName = list.get(list.size()-1).getName();
            String message = error.getMessage();
            String value = error.getInvalidValue().toString();

            Error errorMessage = new Error();
            errorMessage.setField(fieldName);
            errorMessage.setMessage(message);
            errorMessage.setInvalidValue(value);

            errorList.add(errorMessage);

        });

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        errorResponseDto.setResultCode("Fail");
        errorResponseDto.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponseDto.setErrors(errorList);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

}
