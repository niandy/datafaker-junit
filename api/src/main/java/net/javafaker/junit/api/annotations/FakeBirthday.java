package net.javafaker.junit.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@FakeData
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FakeBirthday {
    int minAge() default 18;
    int maxAge() default 65;
    String pattern() default "yyyy-MM-dd HH:mm:ss";
}
