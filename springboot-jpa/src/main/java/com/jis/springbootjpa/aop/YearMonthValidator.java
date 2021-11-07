package com.jis.springbootjpa.aop;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class YearMonthValidator implements ConstraintValidator<YearMonth,String> {

    private String pattern;
    @Override
    public void initialize(YearMonth constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        System.out.println("value" + value);
        //yyyyMM
        try {
            LocalDate lOcalDate = LocalDate.parse(value+"01", DateTimeFormatter.ofPattern(this.pattern));

        }catch (Exception e){
            return false;
        }

        return true;
    }
}
