package net.javafaker.junit.extension.instantitators;

import net.javafaker.junit.extension.DataFakerExtensionContextHolder;

abstract class AbstractInstantiator implements Instantiator {
    public final Object newInstance(Class<?> klass) {
        var context = DataFakerExtensionContextHolder.getThreadContext();
        if (!context.push(klass)) {
            return null;
        }
        try {
            return createInstance(klass);
        } finally {
            context.pop();
        }
    }

    protected abstract Object createInstance(Class<?> klass);
}
