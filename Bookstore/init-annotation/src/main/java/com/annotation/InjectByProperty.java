package com.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.annotation.Types.DEFAULT;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectByProperty {
    String configName() default "";
    String propertyName() default  "";
    Types type() default DEFAULT;
}
