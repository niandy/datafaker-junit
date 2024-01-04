package net.javafaker.junit.api.annotations;

import net.javafaker.junit.api.enums.ProvidedBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@FakeData
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FakeProvided {
    String name();
    ProvidedBy providedBy() default ProvidedBy.METHOD;
    String key();
}
