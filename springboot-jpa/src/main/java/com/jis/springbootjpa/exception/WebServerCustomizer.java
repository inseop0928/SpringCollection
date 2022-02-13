package com.jis.springbootjpa.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    /*에러페이지 반환설정
    사용안하면 스프링 부트에서 기본으로 에러페이지 제공*/
    //RequestDispatcher 참고하면 에서 에러 시 request에서 반환하는 상수 정보가 있음
    
    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        //에러페이지를 등록
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,"/error-page/404");//404 발생시 /error-page/404 컨트롤러에 요청
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,"/error-page/500");//500
        ErrorPage errorPageException = new ErrorPage(RuntimeException.class,"/error-page/500");//runtimeException이 발생했을때(자식도 포함)

        factory.addErrorPages(errorPage404,errorPage500,errorPageException);
    }
}
