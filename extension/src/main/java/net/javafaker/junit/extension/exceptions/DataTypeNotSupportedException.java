package net.javafaker.junit.extension.exceptions;

import java.text.MessageFormat;

public class DataTypeNotSupportedException extends RuntimeException{
    public DataTypeNotSupportedException(Class<?> dataType, String handler) {
        super(MessageFormat.format("Data type {0} is not supported by {1} handler",
                dataType.getCanonicalName(),
                handler));
    }
}
