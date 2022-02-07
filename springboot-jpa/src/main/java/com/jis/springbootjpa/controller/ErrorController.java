package com.jis.springbootjpa.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class ErrorController {

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response){

        log.info("error 404");
        return "/error/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage50(HttpServletRequest request, HttpServletResponse response){

        log.info("error 500");
        return "/error/500";
    }
}
