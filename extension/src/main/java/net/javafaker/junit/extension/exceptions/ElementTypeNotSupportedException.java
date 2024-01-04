package net.javafaker.junit.extension.exceptions;

import java.text.MessageFormat;

public class ElementTypeNotSupportedException extends RuntimeException {
    public ElementTypeNotSupportedException(Class<?> klass) {
        super(MessageFormat.format("AnnotatedElement of type {0} is not supported yet", klass.getCanonicalName()));
    }
}
