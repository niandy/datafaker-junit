package net.javafaker.junit.api.annotations;

import net.javafaker.junit.api.enums.InstantiatorsEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FakeData {
    InstantiatorsEnum instantiateWith() default InstantiatorsEnum.ANY;

}
