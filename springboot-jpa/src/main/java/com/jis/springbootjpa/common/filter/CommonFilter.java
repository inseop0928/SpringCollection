package com.jis.springbootjpa.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.LogRecord;

//필터단에서 요청을 변경 가능
@Slf4j
@WebFilter(urlPatterns = "/api/*")//스캔범위를 지정하려는 경우 webfilter로 설정하는 경우는 필터순서가 조절이 안 된다.
//@Component//전역으로 지정하려는경우
public class CommonFilter  implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //전처리
        ContentCachingRequestWrapper  httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        String url = httpServletRequest.getRequestURI();
        BufferedReader br = httpServletRequest.getReader();

        chain.doFilter(httpServletRequest,httpServletResponse);//변환시킨 response,request를 넘겨줌


        //후처리
        String reqcontent = new String(httpServletRequest.getContentAsByteArray());
        String resContent = new String(httpServletResponse.getContentAsByteArray());
        int httpStatus = httpServletResponse.getStatus();

        /*커서 단위로 읽어버려서 이미 다 읽은 상태에서 컨트롤러로 넘기기 때문에 컨트롤러에서 내용이 없는 상태로 넘어감
        따라서 IllegalStateException getReader() has already been called for this request 오류 발생
        해결 contentCaching사용 : 클래스에 캐싱이 담겨져 있기 때문에 몇번이고 넘겨도 읽을 수있음*/
        //다 읽고 그냥 보내면 응답을 받을 수 없기 떄문에 copy한다.
        httpServletResponse.copyBodyToResponse();

        log.info("reqcontent : {} resContent :{} httpStatus : {}",reqcontent,resContent,httpStatus);

    }
}
