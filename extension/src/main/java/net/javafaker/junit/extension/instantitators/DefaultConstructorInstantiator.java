package net.javafaker.junit.extension.instantitators;

import lombok.extern.flogger.Flogger;
import net.javafaker.junit.extension.DataFakerObjectFactory;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;


@Flogger
class DefaultConstructorInstantiator extends AbstractInstantiator {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    @Override
    protected Object createInstance(Class<?> klass) {
        try {
            var constructor = LOOKUP.findConstructor(klass, MethodType.methodType(void.class));
            var o = constructor.invoke();
            DataFakerObjectFactory.processInstance(o, false);
            return o;
        } catch (Throwable e) {
            log.atFiner()
                .withCause(e)
                .log();
            log.atWarning()
                .log("Can't instantiate class %s using no args constructor", klass.getCanonicalName());
            return null;
        }
    }
}
