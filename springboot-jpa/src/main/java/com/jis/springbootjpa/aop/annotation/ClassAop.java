package com.jis.springbootjpa.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//class에 사용하는경우 지정
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassAop {
}
