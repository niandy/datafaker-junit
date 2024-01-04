package net.javafaker.junit.extension.exceptions;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;

public class HandlerNotFoundException extends RuntimeException {
    public HandlerNotFoundException(Class<? extends Annotation> klass) {
        super(MessageFormat.format("Cannot find handler for annotation {0}", klass.getCanonicalName()));
    }
}
