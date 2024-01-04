package net.javafaker.junit.extension.handlers;

import lombok.SneakyThrows;
import net.datafaker.Faker;
import net.javafaker.junit.api.annotations.FakeProvided;
import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;
import net.datafaker.providers.base.ProviderRegistration;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
class FakeProvidedAnnotationHandler extends AbstractAnnotationBasedHandler {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    @Override
    public boolean isDataTypeSupported(Class<?> type) {
        return true;
    }

    @Override
    public List<Class<? extends Annotation>> getSupportedAnnotations() {
        return Collections.singletonList(FakeProvided.class);
    }

    @Override
    public Object handleInternal(Class<?> type, Annotation annotation) {
        var fakeProvided = (FakeProvided) annotation;
        return switch (fakeProvided.providedBy()) {
            case METHOD -> handleProvidedByMethod(type, getFaker(), fakeProvided);
            case CLASSNAME -> handleProvidedByClass(type, getFaker(), fakeProvided);
        };

    }

    @SneakyThrows
    private Object handleProvidedByMethod(Class<?> type, Faker faker, FakeProvided annotation) {
        var providerMethod = Faker.class.getMethod(annotation.name());
        var provider = providerMethod.invoke(faker);
        var method = LOOKUP.findVirtual(provider.getClass(), annotation.key(), MethodType.methodType(type));
        return method.invoke(provider);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private Object handleProvidedByClass(Class<?> type, Faker faker, FakeProvided annotation) {
        var klass = Class.forName(annotation.name());
        if (!AbstractProvider.class.isAssignableFrom(klass)) {
            throw new ClassCastException(MessageFormat.format("Class {0} cannot be cast to {1}",
                    klass.getCanonicalName(),
                    AbstractProvider.class.getCanonicalName()));
        }
        var provider = faker.getProvider((Class<AbstractProvider<ProviderRegistration>>) klass,
                f -> createProvider(klass, f));
        var method = LOOKUP.findVirtual(klass, annotation.key(), MethodType.methodType(type));
        return method.invoke(provider);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private <T extends ProviderRegistration, R extends AbstractProvider<T>> R createProvider(Class<?> klass, T pr) {
        var init = klass.getDeclaredConstructor(BaseProviders.class);
        init.setAccessible(true);
        return (R) init.newInstance(pr);
    }
}
