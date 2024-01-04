package net.javafaker.junit.extension.handlers;

import java.lang.annotation.Annotation;

class EnumHandler implements FakeDataHandler {
    @Override
    public boolean isDataTypeSupported(Class<?> type) {
        return type.isEnum();
    }

    @Override
    public Object handle(Class<?> type, Annotation annotation) {
        var values = type.getEnumConstants();
        var index = getFaker().random().nextInt(0, values.length - 1);
        return values[index];
    }
}
