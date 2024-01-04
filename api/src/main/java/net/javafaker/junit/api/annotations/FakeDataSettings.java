package net.javafaker.junit.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Random;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FakeDataSettings {
    String languageTag() default "en";
    Class<? extends Random> random() default Random.class;
}
