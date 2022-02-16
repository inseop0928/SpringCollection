package com.jis.springbootjpa.common.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//오류를 제어 및 해결
@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
    
        
        //500에러를 400으로 변환
        if(e instanceof IllegalArgumentException){

            try {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,e.getMessage());
                return new ModelAndView();

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return null;//null로 반환해야 기존에 발생한 예외를 서블릿 밖으로 던진다.
    }
}
