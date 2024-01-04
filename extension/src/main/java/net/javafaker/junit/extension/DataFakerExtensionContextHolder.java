package net.javafaker.junit.extension;

import net.javafaker.junit.extension.exceptions.ContextMissingException;

import java.util.Locale;
import java.util.Random;

public class DataFakerExtensionContextHolder {

    private DataFakerExtensionContextHolder() {}
    private static final ThreadLocal<DataFakerExtensionContext> threadContext = new ThreadLocal<>();

    static void setContext(Locale locale, Random random) {
        threadContext.set(new DataFakerExtensionContext(locale, random));
    }

    public static DataFakerExtensionContext getThreadContext() {
        var context = threadContext.get();
        if (context==null) {
            throw new ContextMissingException();
        }
        return context;
    }

}
