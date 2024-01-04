package net.javafaker.junit.extension.exceptions;

import java.text.MessageFormat;

public class InferringHandlerException extends RuntimeException {
    public InferringHandlerException(Class<?> klass) {
        super(MessageFormat.format("Cannot infer handler from type {0}", klass.getCanonicalName()));
    }
}
