package net.javafaker.junit.extension.handlers;

import lombok.extern.flogger.Flogger;
import net.javafaker.junit.api.annotations.FakeData;
import net.javafaker.junit.extension.exceptions.AnnotationMismatchException;
import net.javafaker.junit.extension.instantitators.InstantiatorRegistry;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;

@Flogger
class FakeDefaultHandler implements FakeDataHandler {
    @Override
    public boolean isDataTypeSupported(Class<?> type) {
        return !type.isPrimitive()
                && !String.class.equals(type)
                && !Collection.class.isAssignableFrom(type);
    }

    @Override
    public Object handle(Class<?> type, Annotation annotation) {
        try {
            if (annotation!=null && !(annotation instanceof FakeData))
                throw new AnnotationMismatchException(Collections.singletonList(FakeData.class), annotation.annotationType());
            var instantiators = InstantiatorRegistry.getInstantiatorsForAnnotation((FakeData) annotation);
            for (var instantiator : instantiators) {
                var value = instantiator.newInstance(type);
                if (value != null) {
                    return value;
                }
            }
        } catch (Exception e) {
            log.atFiner()
                    .withCause(e)
                    .log();
            log.atWarning()
                    .log("cant create instance of %s", type.getCanonicalName());
        }
        return null;
    }

}
