package com.jis.springbootjpa.formatter;


import com.jis.springbootjpa.converter.IpPortToStringConverter;
import com.jis.springbootjpa.converter.StringToIpPortConverter;
import com.jis.springbootjpa.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.Assert.*;

public class NumberFormatterTest {


    NumberFormatter numberFormatter = new NumberFormatter();

    @Test
    public void parse() throws ParseException{
        Number number = numberFormatter.parse("1000", Locale.KOREA);
        Assertions.assertThat(number).isEqualTo(1000L);//long 타입(Number는 추상클래스이므로 구체적으로는 long타입을 리턴한다.)
    }

    @Test
    public void print(){
        String result = numberFormatter.print(1000,Locale.KOREA);
        Assertions.assertThat(result).isEqualTo("1,000");//long 타입
    }

    @Test
    public void formattingConversionService(){
        //컨버터,포맷터 등록
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        
        //컨버터(객체->객체) 등록
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        //포맷터(객체->문자 or 문자->객체)
        conversionService.addFormatter(new NumberFormatter());

        IpPort ipPort = conversionService.convert("127.0.0.1:8080",IpPort.class);
        Assertions.assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1",8080));

        Assertions.assertThat(conversionService.convert(1000,String.class)).isEqualTo("1,000");
        Assertions.assertThat(conversionService.convert("1,000",Long.class)).isEqualTo(1000L);
    }
}