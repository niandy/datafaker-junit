package net.javafaker.junit.extension.handlers;

import net.javafaker.junit.extension.exceptions.AnnotationMismatchException;

import java.lang.annotation.Annotation;
import java.util.List;

abstract class AbstractAnnotationBasedHandler implements FakeDataHandler {
    @Override
    @SuppressWarnings("unchecked")
    public final Object handle(Class<?> type, Annotation annotation) {
        var supported = getSupportedAnnotations();
        if (!supported.contains(annotation.annotationType()))
            throw new AnnotationMismatchException(supported, annotation.annotationType());
        return handleInternal(type, annotation);
    }

    public abstract List<Class<? extends Annotation>> getSupportedAnnotations();

    protected abstract Object handleInternal(Class<?> type, Annotation annotation);
}
