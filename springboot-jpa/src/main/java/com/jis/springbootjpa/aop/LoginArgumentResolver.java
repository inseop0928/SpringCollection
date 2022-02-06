package com.jis.springbootjpa.aop;

import com.jis.springbootjpa.constant.SessionConst;
import com.jis.springbootjpa.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        log.info("supportsParameter");

        boolean hasParameter = methodParameter.hasParameterAnnotation(Login.class);
        boolean assignableFrom = Member.class.isAssignableFrom(methodParameter.getParameterType());

        return hasParameter && assignableFrom;//여기서 true가 나와야 다음으로 넘어감
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        log.info("resolveArgument");
        HttpServletRequest request = (HttpServletRequest)nativeWebRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if(session == null){
            return null;
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER_KEY);
    }
}
