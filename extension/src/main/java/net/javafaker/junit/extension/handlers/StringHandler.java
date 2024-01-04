package net.javafaker.junit.extension.handlers;

import java.lang.annotation.Annotation;

class StringHandler implements FakeDataHandler {

    public static final int MINIMUM_LENGTH = 5;
    public static final int MAXIMUM_LENGTH = 15;

    @Override
    public boolean isDataTypeSupported(Class<?> type) {
        return type.equals(String.class);
    }

    @Override
    public Object handle(Class<?> type, Annotation annotation) {
        return getFaker().lorem().characters(MINIMUM_LENGTH, MAXIMUM_LENGTH);
    }
}
