package net.javafaker.junit.extension.handlers;

import net.javafaker.junit.api.annotations.FakeCollection;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

public interface CollectionSupportHandler extends  FakeDataHandler {
    Object handleIterable(Class<?> type,
                                 @Nullable Annotation annotation,
                                 @Nullable FakeCollection collectionAnnotation);
}
