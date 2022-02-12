package com.jis.springbootjpa.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
      log.info("LogFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        try{
            log.info("REQUEST [{}] [{}] [{}]", uuid, request.getDispatcherType(),requestURI);
            filterChain.doFilter(request,response);//다음필터를 호출 또는 없으면 서블릿을 호출
        }catch (Exception e){
            log.info("EXCEPTION [{}] ",e.getMessage());
            throw e;
        }finally {
            log.info("RESPONSE [{}] [{}] [{}]", uuid, request.getDispatcherType(),requestURI);
        }

    }

    @Override
    public void destroy() {
        log.info("LogFilter destroy");
    }
}
