package net.javafaker.junit.extension.exceptions;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;
import java.util.List;

public class AnnotationMismatchException extends RuntimeException {
    public AnnotationMismatchException(List<Class<? extends Annotation>> supported, Class<? extends Annotation> actual) {
        super(MessageFormat.format("Bad annotation class. Wanted {0}, got {1}",
                getClassNames(supported),
                actual.getCanonicalName()));
    }

    private static String getClassNames(List<Class<? extends Annotation>> annotations) {
        return annotations
                .stream()
                .map(Class::getCanonicalName)
                .toList()
                .toString();
    }
}
