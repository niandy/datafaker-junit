package net.javafaker.junit.extension.handlers;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.javafaker.junit.api.annotations.FakeData;
import net.javafaker.junit.api.annotations.FakeEnum;
import net.javafaker.junit.extension.exceptions.HandlerNotFoundException;
import net.javafaker.junit.extension.exceptions.InferringHandlerException;
import org.junit.platform.commons.util.ClassFilter;
import org.junit.platform.commons.util.ReflectionUtils;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class HandlerRegistry {
    private final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private final int NON_INSTANTIABLE = Modifier.INTERFACE | Modifier.ABSTRACT;
    private final Map<Class<? extends Annotation>, FakeDataHandler> HANDLERS = new HashMap<>();
    private final FakeDataHandler DEFAULT_HANDLER = new FakeDefaultHandler();
    final FakeDataHandler PRIMITIVE_HANDLER = new PrimitiveTypeHandler();
    private final FakeDataHandler STRING_HANDLER = new StringHandler();
    private final EnumHandler ENUM_HANDLER = new EnumHandler();
    private final FakeCollectionHandler COLLECTION_HANDLER = new FakeCollectionHandler();

    @SneakyThrows
    private void registerHandler(Class<?> handlerClass) {
        var ctor = LOOKUP.findConstructor(handlerClass, MethodType.methodType(void.class));
        var handler = (AbstractAnnotationBasedHandler) ctor.invoke();
        handler
                .getSupportedAnnotations()
                .forEach(annotation -> HANDLERS.put(annotation, handler));
    }

    static {
        var classes = ReflectionUtils.findAllClassesInPackage(HandlerRegistry.class.getPackageName(),
                ClassFilter.of(klass -> !AbstractAnnotationBasedHandler.class.equals(klass) &&
                                AbstractAnnotationBasedHandler.class.isAssignableFrom(klass)));
        classes.forEach(HandlerRegistry::registerHandler);
        HANDLERS.put(FakeData.class, DEFAULT_HANDLER);
        HANDLERS.put(FakeEnum.class, ENUM_HANDLER);
    }


    public @NonNull FakeDataHandler getHandler(Class<?> dataType,
                                                      @Nullable Class<? extends Annotation> annotation,
                                                      @Nullable Class<? extends Annotation> collectionAnnotation) {
        if (isCollection(dataType, collectionAnnotation))
            return COLLECTION_HANDLER;
        if (annotation!=null) {
            var handler = HANDLERS.get(annotation);
            if (handler==null)
                throw new HandlerNotFoundException(annotation);
            return handler;
        }
        return getDataTypeHandler(dataType);
    }

    private boolean isCollection(Class<?> dataType,
                                 @Nullable Class<? extends Annotation> collectionAnnotation) {
        if (byte[].class.equals(dataType) || char[].class.equals(dataType))
            return false;
        if (COLLECTION_HANDLER.isDataTypeSupported(dataType))
            return true;
        return (collectionAnnotation!=null);
    }
    private FakeDataHandler getDataTypeHandler(Class<?> dataType) {
        if (PRIMITIVE_HANDLER.isDataTypeSupported(dataType))
            return PRIMITIVE_HANDLER;
        else if (STRING_HANDLER.isDataTypeSupported(dataType))
            return STRING_HANDLER;
        else if (ENUM_HANDLER.isDataTypeSupported(dataType))
            return ENUM_HANDLER;
        else if ((dataType.getModifiers() & NON_INSTANTIABLE) != 0) {
            throw new InferringHandlerException(dataType);
        }
        return DEFAULT_HANDLER;
    }
}

