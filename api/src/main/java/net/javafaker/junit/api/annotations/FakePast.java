package net.javafaker.junit.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@FakeData
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FakePast {
    int minimum() default 0;
    int atMost();
    TimeUnit unit();
    String pattern() default "yyyy-MM-dd HH:mm:ss";
}
