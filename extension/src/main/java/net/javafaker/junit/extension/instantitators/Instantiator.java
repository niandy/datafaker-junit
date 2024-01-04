package net.javafaker.junit.extension.instantitators;

public interface Instantiator {
    Object newInstance(Class<?> klass);
}
