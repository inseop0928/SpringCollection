package com.jis.springbootjpa.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class NumberFormatter implements Formatter<Number> {
    @Override
    public Number parse(String s, Locale locale) throws ParseException {
        log.info("text={} , locale={}",s,locale);

        NumberFormat format = NumberFormat.getInstance(locale);

        return format.parse(s);
    }

    @Override
    public String print(Number number, Locale locale) {

        log.info("number {}, locale={}",number,locale);
        NumberFormat format = NumberFormat.getInstance(locale);
        String strNum  = format.format(number);
        return strNum;
    }
}
