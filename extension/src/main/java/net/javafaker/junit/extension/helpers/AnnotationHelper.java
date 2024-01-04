package net.javafaker.junit.extension.helpers;

import lombok.experimental.UtilityClass;
import net.javafaker.junit.api.annotations.FakeCollection;
import net.javafaker.junit.api.annotations.FakeData;
import net.javafaker.junit.api.annotations.FakeDataSettings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class AnnotationHelper {

    public @Nonnull Optional<Annotation> findFakeCollectionAnnotation(@Nonnull AnnotatedElement element) {
        return findFakeAnnotation(element, FakeCollection.class);
    }

    public @Nonnull Optional<Annotation> findFakeCollectionAnnotation(Class<?> klass, @Nonnull String fieldName) {
        var fields = ReflectionHelper.getFieldsForClass(klass, field -> field.getName().equals(fieldName));
        return fields
                .stream()
                .findFirst()
                .flatMap(AnnotationHelper::findFakeCollectionAnnotation);
    }
    public @Nonnull Optional<Annotation> findFakeDataAnnotation(Class<?> klass, @Nonnull String fieldName) {
        var fields = ReflectionHelper.getFieldsForClass(klass, field -> field.getName().equals(fieldName));
        return fields
                .stream()
                .findFirst()
                .flatMap(AnnotationHelper::findFakeDataAnnotation);
    }

    public @Nonnull Optional<Annotation> findFakeDataAnnotation(@Nonnull AnnotatedElement element) {
       return findFakeAnnotation(element, FakeData.class);
    }

    @Nonnull Optional<Annotation> findFakeAnnotation(@Nonnull AnnotatedElement element,
                                                     @Nonnull Class<? extends Annotation> annotationType) {
        var annotations = element.getAnnotations();
        for(var annotation: annotations) {
            if (isFakeAnnotation(annotation, annotationType)) {
                return Optional.of(annotation);

            }
        }
        return Optional.empty();
    }

    public @Nullable FakeDataSettings findSettings(@Nonnull List<Object> instances) {
        for (var instance: instances) {
            var annotations = instance.getClass().getDeclaredAnnotationsByType(FakeDataSettings.class);
            if (annotations.length > 0)
                return annotations[0];
        }
        return null;
    }


    private boolean isFakeAnnotation(Annotation annotation, Class<? extends Annotation> annotationType) {
        var klass = annotation.annotationType();
        if (klass.equals(annotationType))
            return true;
        var parents = klass.getDeclaredAnnotationsByType(annotationType);
        return parents.length > 0;
    }
}
