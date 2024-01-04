package net.javafaker.junit.extension.handlers;

import net.javafaker.junit.api.annotations.FakeBytes;
import net.javafaker.junit.api.annotations.FakeDouble;
import net.javafaker.junit.api.annotations.FakeInteger;
import net.javafaker.junit.api.annotations.FakeNumber;
import net.javafaker.junit.extension.exceptions.AnnotationMismatchException;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;
import java.util.List;

@SuppressWarnings("unused")
class FakeNumberAnnotationHandler extends AbstractAnnotationBasedHandler {

    @Override
    public List<Class<? extends Annotation>> getSupportedAnnotations() {
        return List.of(
                FakeNumber.class,
                FakeInteger.class,
                FakeDouble.class,
                FakeBytes.class
        );

    }

    @Override
    protected Object handleInternal(Class<?> type, Annotation annotation) {
        if (annotation instanceof FakeInteger fakeInteger)
            return fakeInteger(fakeInteger);
        else if (annotation instanceof FakeDouble fakeDouble)
            return fakeDouble(fakeDouble);
        else if (annotation instanceof FakeBytes fakeBytes)
            return fakeBytes(type, fakeBytes);
        else if (annotation instanceof FakeNumber fakeNumber)
            return HandlerRegistry.PRIMITIVE_HANDLER.handle(type, fakeNumber);
        else
            throw new AnnotationMismatchException(getSupportedAnnotations(), annotation.annotationType());
    }

    @Override
    public boolean isDataTypeSupported(Class<?> type) {
        return Number.class.isAssignableFrom(type)
                || type.isPrimitive()
                || byte[].class.equals(type);
    }

    private Object fakeBytes(Class<?> type, FakeBytes annotation) {
        if (char[].class.equals(type)) {
            return getFaker().letterify("?".repeat(annotation.length())).toCharArray();
        }
        return getFaker().random().nextRandomBytes(annotation.length());
    }

    private Object fakeInteger(FakeInteger annotation) {
        return getFaker().random().nextLong(annotation.min(), annotation.max());
    }

    private Object fakeDouble(FakeDouble annotation) {
        return getFaker().random().nextDouble(annotation.min(), annotation.max());
    }
}
