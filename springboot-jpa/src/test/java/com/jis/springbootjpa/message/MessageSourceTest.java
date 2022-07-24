package com.jis.springbootjpa.message;


import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)//jnint4에서 @SpringBootTest만 쓰면 nullPoint 에러가 발생하므로 추가
@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    public void messageSourceTest(){
        String result = messageSource.getMessage("hello",null,null);//locale이 없는 경우 기본 messageProperty를 선택
        Assertions.assertThat(result).isEqualTo("hello" );
    }

    @Test
    public void notFoundMessage(){

        assertThatThrownBy(()->messageSource.getMessage("no_code",null,null))
        .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    public void defaultMessage(){
        String result = messageSource.getMessage("no_code",null,"defaultValue",null);

        Assertions.assertThat(result).isEqualTo("defaultValue");

    }

    @Test
    public void paramMessage(){
        String result = messageSource.getMessage("hello.name",new Object[]{"spring"},null);//파라미터를 배열형태로 넘김
        Assertions.assertThat(result).isEqualTo("hello spring");
    }

    @Test
    public void getEnMessage(){
        String result = messageSource.getMessage("hello",null, Locale.ENGLISH);
        Assertions.assertThat(result).isEqualTo("hello");

    }

}
