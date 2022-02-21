package com.jis.springbootjpa.controller;


import com.jis.springbootjpa.common.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ErrorController {
    
    //RequestDispatcher에 상수로 정의되어 있음
    String FORWARD_REQUEST_URI = "javax.servlet.forward.request_uri";
    String FORWARD_CONTEXT_PATH = "javax.servlet.forward.context_path";
    String FORWARD_MAPPING = "javax.servlet.forward.mapping";
    String FORWARD_PATH_INFO = "javax.servlet.forward.path_info";
    String FORWARD_SERVLET_PATH = "javax.servlet.forward.servlet_path";
    String FORWARD_QUERY_STRING = "javax.servlet.forward.query_string";
    String INCLUDE_REQUEST_URI = "javax.servlet.include.request_uri";
    String INCLUDE_CONTEXT_PATH = "javax.servlet.include.context_path";
    String INCLUDE_PATH_INFO = "javax.servlet.include.path_info";
    String INCLUDE_MAPPING = "javax.servlet.include.mapping";
    String INCLUDE_SERVLET_PATH = "javax.servlet.include.servlet_path";
    String INCLUDE_QUERY_STRING = "javax.servlet.include.query_string";
    String ERROR_EXCEPTION = "javax.servlet.error.exception";
    String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    String ERROR_MESSAGE = "javax.servlet.error.message";
    String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    String ERROR_STATUS_CODE = "javax.servlet.error.status_code";
    @RequestMapping("/error/ex")
    public void errorEx(HttpServletRequest request, HttpServletResponse response){
        throw new RuntimeException("에러처리");

    }

    @RequestMapping("/error-page/404")
    public void errorPage404(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.info("error 404");
        response.sendError(404,"404오류");//스프링에서 알아서 상태코드에 따랑 뷰랑 매핑을 시커줌
    }

    //@RequestMapping("/error-page/404")
    public String errorPage404_old(HttpServletRequest request, HttpServletResponse response){

        log.info("error 404");
        return "/error/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response){

        log.info("error 500");
        return "/error/500";
    }

    //클라이언트 applicationType Json인 경우
    //같은 매핑주소가 있는경우 더 우선 순위
    @RequestMapping(value="/error-page/500" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> errorPage500Api(HttpServletRequest request, HttpServletResponse response){

        log.info("API errorPage 500");
        Map<String,Object> result = new HashMap<>();

        Exception exception = (Exception)request.getAttribute(ERROR_EXCEPTION);
        result.put("status",request.getAttribute(ERROR_STATUS_CODE));
        result.put("message", exception.getMessage());

        Integer statusCode = (Integer)request.getAttribute(ERROR_STATUS_CODE);

        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));

    }
}
