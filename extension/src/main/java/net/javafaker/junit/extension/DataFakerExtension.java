package net.javafaker.junit.extension;

import net.javafaker.junit.api.annotations.FakeCollection;
import net.javafaker.junit.api.annotations.FakeData;
import net.javafaker.junit.api.annotations.FakeDataSettings;
import net.javafaker.junit.extension.helpers.AnnotationHelper;
import org.junit.jupiter.api.extension.*;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class DataFakerExtension implements ParameterResolver, TestInstancePostProcessor {

    private void createContext(@Nullable FakeDataSettings settings) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Locale locale;
        Random random;
        if (settings==null) {
            locale = Locale.getDefault();
            random = new Random();
        } else {
            locale = Locale.forLanguageTag(settings.languageTag());
            random = settings.random().getDeclaredConstructor().newInstance();

        }
        DataFakerExtensionContextHolder.setContext(locale, random);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(FakeData.class) ||
                parameterContext.isAnnotated(FakeCollection.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return DataFakerObjectFactory.instanceForElement(parameterContext.getParameter(), null);
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        var settings = AnnotationHelper.findSettings(Collections.singletonList(testInstance));
        createContext(settings);
        DataFakerObjectFactory.processInstance(testInstance, true);
    }
}
