package com.jis.springbootjpa.aop;

import com.jis.springbootjpa.controller.HomeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)//jnint4에서 @SpringBootTest만 쓰면 nullPoint 에러가 발생하므로 추가
@SpringBootTest
public class TestAspectTest {

    @Autowired
    HomeController homeController;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void isAopTest(){
        logger.info("isAopProxy {}", AopUtils.isAopProxy(homeController));//
    }

}