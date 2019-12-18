package com.yuanjun.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemServiceLog {
     /** Ҫִ�еĲ������ͱ��磺add���� **/  
     public String operationType() default "";  
      
     /** Ҫִ�еľ���������磺����û� **/  
     public String operationName() default "";
}

