package net.javafaker.junit.extension.instantitators;

import lombok.SneakyThrows;
import lombok.extern.flogger.Flogger;
import net.javafaker.junit.extension.helpers.AnnotationHelper;
import net.javafaker.junit.extension.DataFakerObjectFactory;
import net.javafaker.junit.extension.helpers.ReflectionHelper;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@Flogger
class BuilderInstantiator extends AbstractInstantiator {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    @Override
    protected Object createInstance(Class<?> klass) {
        try {
            var builderClass = Class.forName(getBuilderClassName(klass));
            MethodHandle builderMethod = LOOKUP.findStatic(klass, "builder", MethodType.methodType(builderClass));
            MethodHandle buildMethod = LOOKUP.findVirtual(builderClass, "build", MethodType.methodType(klass));
            var builder = builderMethod.invoke();
            setFields(builder, klass);
            return buildMethod.invoke(builder);
        } catch (Throwable e) {
            log.atFiner()
                .withCause(e)
                .log();
            log.atWarning()
                .log("Can't instantiate class %s using builder", klass.getCanonicalName());
            return null;
        }
    }

    private void setFields(Object builder, Class<?> originalClass) {
        var methods = builder.getClass().getDeclaredMethods();
        Arrays.stream(methods)
                .filter(method -> method.getReturnType().equals(builder.getClass()))
                .filter(method -> method.getParameterCount() == 1)
                .filter(method -> Modifier.isPublic(method.getModifiers()))
                .forEach(method -> setField(builder, method, originalClass));
    }

    @SneakyThrows
    private void setField(Object builder, Method method, Class<?> originalClass) {
        var params = method.getParameters();
        var field = ReflectionHelper.getFieldsForClass(originalClass, field1 -> field1.getName().equals(method.getName()))
                .stream()
                .findFirst()
                .orElse(null);
        var o = DataFakerObjectFactory.instanceForElement(params[0], field);
        method.invoke(builder, o);
    }

    private String getBuilderClassName(Class<?> klass) {
        return klass.getCanonicalName() +
                "$" +
                klass.getSimpleName() +
                "Builder";
    }
}
