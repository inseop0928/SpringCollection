package com.jis.springbootjpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;

class SpringbootJpaApplicationTests {

    @Test
    void encoding() {
        String taget = "inseop@gmail.com";

        System.out.println(Base64.getEncoder().encodeToString(taget.getBytes()));

    }

}
