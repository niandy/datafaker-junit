package net.javafaker.junit.extension;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.javafaker.junit.api.annotations.FakeCollection;
import net.javafaker.junit.extension.exceptions.DataTypeNotSupportedException;
import net.javafaker.junit.extension.exceptions.ElementTypeNotSupportedException;
import net.javafaker.junit.extension.handlers.CollectionSupportHandler;
import net.javafaker.junit.extension.handlers.HandlerRegistry;
import net.javafaker.junit.extension.helpers.AnnotationHelper;
import net.javafaker.junit.extension.helpers.ReflectionHelper;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Optional;

@UtilityClass
public class DataFakerObjectFactory {

    // todo: support @FakeCollection

    public @Nullable Object instanceForElement(AnnotatedElement element,
                                               @Nullable AnnotatedElement backingElement) {
        Annotation fakeAnnotation, collectionAnnotation;
        var fakeAnnotationMain = AnnotationHelper.findFakeDataAnnotation(element);
        var collectionAnnotationMain = AnnotationHelper.findFakeCollectionAnnotation(element);
        if (backingElement!=null) {
            fakeAnnotation = fakeAnnotationMain.orElseGet(() ->
                    AnnotationHelper.findFakeDataAnnotation(backingElement).orElse(null));
            collectionAnnotation = collectionAnnotationMain.orElseGet(() ->
                    AnnotationHelper.findFakeCollectionAnnotation(backingElement).orElse(null));
        } else {
            fakeAnnotation = fakeAnnotationMain.orElse(null);
            collectionAnnotation = collectionAnnotationMain.orElse(null);
        }
        return newInstance(getElementType(element), fakeAnnotation, collectionAnnotation);
    }

    public @Nullable Object newInstance(Class<?> type,
                                         @Nullable Annotation annotation,
                                         @Nullable Annotation collectionAnnotation) {
        var handler = HandlerRegistry.getHandler(type,
                Optional.ofNullable(annotation).map(Annotation::annotationType).orElse(null),
                Optional.ofNullable(collectionAnnotation).map(Annotation::annotationType).orElse(null));
        if (!handler.isDataTypeSupported(type)) {
            throw new DataTypeNotSupportedException(type, handler.getClass().getCanonicalName());
        }
        if (handler instanceof CollectionSupportHandler collectionHandler)
            return collectionHandler.handleIterable(type, annotation, (FakeCollection) collectionAnnotation);
        return handler.handle(type, annotation);
    }

    public void processInstance(Object testInstance, boolean annotatedFieldsOnly) {
        var fields = ReflectionHelper.getFieldsForClass(testInstance.getClass(), ReflectionHelper.ANYTHING);
        fields.forEach(field -> processField(testInstance, annotatedFieldsOnly, field));
    }

    private void processField(Object testInstance, boolean annotatedFieldsOnly, Field field) {
        var annotation = AnnotationHelper.findFakeDataAnnotation(field);
        var collectionAnnotation = AnnotationHelper.findFakeCollectionAnnotation(field);
        if (!annotatedFieldsOnly || annotation.isPresent() || collectionAnnotation.isPresent()) {
            processField(testInstance,
                    field,
                    annotation.orElse(null),
                    collectionAnnotation.orElse(null));
        }
    }

    @SneakyThrows
    private  void processField(Object instance,
                                     Field field,
                                     @Nullable Annotation annotation,
                                     @Nullable Annotation collectionAnnotation) {
        var result = DataFakerObjectFactory.newInstance(field.getType(),
                annotation,
                collectionAnnotation);
        field.setAccessible(true);
        field.set(instance, result);
    }

    private Class<?> getElementType(AnnotatedElement element) {
        if (element instanceof Field field) {
            return field.getType();
        } else if (element instanceof Parameter parameter) {
            return parameter.getType();
        } else throw new ElementTypeNotSupportedException(element.getClass());
    }

}
