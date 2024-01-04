package net.javafaker.junit.extension.handlers;

import net.javafaker.junit.api.annotations.*;
import net.javafaker.junit.extension.exceptions.AnnotationMismatchException;

import java.lang.annotation.Annotation;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
class FakeDateAnnotationHandler extends AbstractAnnotationBasedHandler {
    @Override
    public List<Class<? extends Annotation>> getSupportedAnnotations() {
        return List.of(FakeFuture.class,
                FakePast.class,
                FakeDuration.class,
                FakeBirthday.class);
    }

    @Override
    public boolean isDataTypeSupported(Class<?> type) {
        return Date.class.isAssignableFrom(type)
                || Duration.class.equals(type)
                || LocalDate.class.equals(type)
                || LocalDateTime.class.equals(type)
                || String.class.equals(type);
    }

    @Override
    protected Object handleInternal(Class<?> type, Annotation annotation) {
        if (annotation instanceof FakeFuture fakeFuture)
            return fakeFuture(type, fakeFuture);
        else if (annotation instanceof FakePast fakePast)
            return fakePast(type, fakePast);
        else if (annotation instanceof FakeBirthday fakeBirthday)
            return fakeBirthday(type, fakeBirthday);
        else if (annotation instanceof FakeDuration fakeDuration)
            return fakeDuration(type, fakeDuration);
        else
            throw new AnnotationMismatchException(getSupportedAnnotations(), annotation.annotationType());
    }

    private Object fakeFuture(Class<?> type, FakeFuture fakeFuture) {
        if (String.class.equals(type))
            return getFaker().date().future(fakeFuture.atMost(), fakeFuture.minimum(), fakeFuture.unit(), fakeFuture.pattern());
        else {
            var result = getFaker().date().future(fakeFuture.atMost(), fakeFuture.minimum(), fakeFuture.unit());
            if (LocalDateTime.class.equals(type))
                return result.toLocalDateTime();
            else if (LocalDate.class.equals(type))
                return result.toLocalDateTime().toLocalDate();
            else
                return result;
        }
    }

    private Object fakePast(Class<?> type, FakePast fakePast) {
        if (String.class.equals(type))
            return getFaker().date().past(fakePast.atMost(), fakePast.minimum(), fakePast.unit(), fakePast.pattern());
        else {
            var result = getFaker().date().past(fakePast.atMost(), fakePast.minimum(), fakePast.unit());
            if (LocalDateTime.class.equals(type))
                return result.toLocalDateTime();
            else if (LocalDate.class.equals(type))
                return result.toLocalDateTime().toLocalDate();
            else
                return result;
        }
    }

    private Object fakeBirthday(Class<?> type, FakeBirthday fakeBirthday) {
        if (String.class.equals(type)) {
            return getFaker().date().birthday(fakeBirthday.minAge(), fakeBirthday.maxAge(), fakeBirthday.pattern());
        } else {
            var result = getFaker().date().birthday(fakeBirthday.minAge(), fakeBirthday.maxAge());
            if (LocalDateTime.class.equals(type))
                return result.toLocalDateTime();
            else if (LocalDate.class.equals(type))
                return result.toLocalDateTime().toLocalDate();
            else
                return result;
        }
    }

    private Object fakeDuration(Class<?> type, FakeDuration fakeDuration) {
        return getFaker().date().duration(fakeDuration.min(), fakeDuration.max(), fakeDuration.unit());
    }

}
