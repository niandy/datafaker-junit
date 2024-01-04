package net.javafaker.junit.extension.instantitators;

import lombok.extern.flogger.Flogger;
import net.javafaker.junit.extension.DataFakerObjectFactory;
import net.javafaker.junit.extension.helpers.AnnotationHelper;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

@Flogger
class RequiredArgsConstructorInstantiator extends AbstractInstantiator {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    @Override
    protected Object createInstance(Class<?> klass) {
        try {
            var constructors = klass.getDeclaredConstructors();
            if (constructors.length == 0)
                throw new NoSuchElementException("RequiredArgsConstructor not found");
            var ctor = Arrays.stream(constructors)
                    .max(Comparator.comparing(Constructor::getParameterCount))
                    .get();
            var ctorHandle = LOOKUP.unreflectConstructor(ctor);
            var parameters = ctor.getParameters();
            var parameterValues = Arrays.stream(parameters)
                    .map(this::resolveParameter)
                    .toList();
            return ctorHandle.invokeWithArguments(parameterValues);
        } catch (Throwable e) {
            log.atFiner()
                .withCause(e)
                .log();
            log.atWarning()
                .log("Can't instantiate class %s using required args constructor", klass.getCanonicalName());
            return null;
        }
    }

    private Object resolveParameter(Parameter parameter) {
        return DataFakerObjectFactory.instanceForElement(parameter, null);
    }
}
