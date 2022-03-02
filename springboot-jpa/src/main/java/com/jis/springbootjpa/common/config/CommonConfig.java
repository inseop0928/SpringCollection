package com.jis.springbootjpa.common.config;


import com.jis.springbootjpa.aop.LoginArgumentResolver;
import com.jis.springbootjpa.common.filter.LogFilter;
import com.jis.springbootjpa.common.filter.LoginCheckFilter;
import com.jis.springbootjpa.common.interceptor.LogInterceptor;
import com.jis.springbootjpa.common.interceptor.LoginCheckInterceptor;
import com.jis.springbootjpa.common.resolver.MyHandlerExceptionResolver;
import com.jis.springbootjpa.common.resolver.UserHandlerExceptionResolver;
import com.jis.springbootjpa.converter.IpPortToStringConverter;
import com.jis.springbootjpa.converter.StringToIntegerConverter;
import com.jis.springbootjpa.converter.StringToIpPortConverter;
import com.jis.springbootjpa.formatter.NumberFormatter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.List;

@Configuration
public class CommonConfig implements WebMvcConfigurer {

    //WebMvcConfigurer에서 상속 받음
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/*.ico","/error");


        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/member/add","/login","/logout","/css**","/*.ico","/error","/error/**","/error-page/**");
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
        resolvers.add(new UserHandlerExceptionResolver());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }

    @Bean
    public FilterRegistrationBean loginFilterBean(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter());
        filterFilterRegistrationBean.setOrder(1);//순서 낮을 수록 먼저 동작
        filterFilterRegistrationBean.addUrlPatterns("/*");
        //필터나 인터셉터가 한 번 더 호출되는것을 막기위해 설정
        filterFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.ERROR);//요청과 에러일때만 처리

        return filterFilterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilterBean(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LoginCheckFilter());
        filterFilterRegistrationBean.setOrder(2);
        filterFilterRegistrationBean.addUrlPatterns("/*");

        return filterFilterRegistrationBean;
    }

    //converter,fomatter  등록
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());
        registry.addFormatter(new NumberFormatter());//1000->1,000 or 1,000->1000

    }
}
