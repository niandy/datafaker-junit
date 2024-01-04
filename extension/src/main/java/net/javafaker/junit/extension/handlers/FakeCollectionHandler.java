package net.javafaker.junit.extension.handlers;

import lombok.SneakyThrows;
import net.javafaker.junit.api.annotations.FakeCollection;
import net.javafaker.junit.extension.DataFakerObjectFactory;
import net.javafaker.junit.extension.exceptions.InferringHandlerException;

import javax.annotation.Nullable;
import javax.naming.OperationNotSupportedException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.Collection;

@SuppressWarnings("unused")
class FakeCollectionHandler implements CollectionSupportHandler {

    @Override
    public Object handleIterable(Class<?> type,
                                 @Nullable Annotation annotation,
                                 @Nullable FakeCollection collectionAnnotation) {
        if (type.isArray())
            return handleArray(type, collectionAnnotation, annotation);
        return handleCollection(type, collectionAnnotation, annotation);
    }

    @SneakyThrows
    @Override
    public Object handle(Class<?> type, Annotation annotation) {
        throw new OperationNotSupportedException("FakeCollection handler should not be used for non-iterable types");
    }

    private Object handleArray(Class<?> type,
                               @Nullable FakeCollection collectionAnnotation,
                               @Nullable Annotation componentAnnotation) {
        var componentType = type.componentType();
        var size = getCollectionSize(collectionAnnotation);
        var result = Array.newInstance(componentType,size);
        for (int i=0;i<size;i++) {
            Array.set(result, i, DataFakerObjectFactory.newInstance(componentType,
                    componentAnnotation,
                    null));
        }
        return result;
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private Object handleCollection(Class<?> type,
                                    @Nullable FakeCollection collectionAnnotation,
                                    @Nullable Annotation componentAnnotation) {
        if (collectionAnnotation==null)
            throw new InferringHandlerException(type);
        var componentType = collectionAnnotation.componentType();
        var size = getCollectionSize(collectionAnnotation);
        var result = (Collection<Object>) type.getDeclaredConstructor().newInstance();
        for (int i=0;i<size;i++) {
            result.add(DataFakerObjectFactory.newInstance(componentType,
                    componentAnnotation,
                    null));
        }
        return result;
    }

    @Override
    public boolean isDataTypeSupported(Class<?> type) {
        return type.isArray() ||
                Collection.class.isAssignableFrom(type);
    }

    private int getCollectionSize(@Nullable FakeCollection collectionAnnotation) {
        if (collectionAnnotation==null) {
            return getFaker().random().nextInt(1, 5);
        }
        return getFaker().random().nextInt(collectionAnnotation.min(), collectionAnnotation.max());
    }
}
