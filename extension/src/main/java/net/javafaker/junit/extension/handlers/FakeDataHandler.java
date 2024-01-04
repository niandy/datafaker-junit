package net.javafaker.junit.extension.handlers;

import net.datafaker.Faker;
import net.javafaker.junit.extension.DataFakerExtensionContextHolder;

import java.lang.annotation.Annotation;

public interface FakeDataHandler {
    boolean isDataTypeSupported(Class<?> type);

    Object handle(Class<?> type, Annotation annotation);

    default Faker getFaker() {
        return DataFakerExtensionContextHolder.getThreadContext().getFaker();
    }
}
