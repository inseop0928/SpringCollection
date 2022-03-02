package com.jis.springbootjpa.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIntegerConverter implements Converter<String,Integer> {

    @Override
    public Integer convert(String s) {
        log.info("convert source={}",s);
        Integer integer = Integer.valueOf(s);
        return integer;
    }
}
