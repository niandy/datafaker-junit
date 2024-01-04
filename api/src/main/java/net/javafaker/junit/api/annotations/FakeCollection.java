package net.javafaker.junit.api.annotations;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FakeCollection {
    int min() default 1;
    int max() default 5;
    Class<?> componentType();
}
