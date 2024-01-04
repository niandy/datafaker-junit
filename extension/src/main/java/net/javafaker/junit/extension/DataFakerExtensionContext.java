package net.javafaker.junit.extension;

import lombok.Getter;
import net.datafaker.Faker;

import java.util.ArrayDeque;
import java.util.Locale;
import java.util.Random;

public class DataFakerExtensionContext {
    private static final int MAX_DEPTH = 5;
    private final ArrayDeque<Class<?>> elements = new ArrayDeque<>();
    private int depth;
    @Getter
    private final Faker faker;

    public DataFakerExtensionContext(Locale locale, Random random) {
        this.faker = new Faker(locale, random);
    }

    public boolean push(Class<?> element) {
        if (elements.contains(element) || depth >= MAX_DEPTH)
            return false;
        depth++;
        return elements.add(element);
    }

    public Object pop() {
        depth--;
        return elements.removeLast();
    }


}
