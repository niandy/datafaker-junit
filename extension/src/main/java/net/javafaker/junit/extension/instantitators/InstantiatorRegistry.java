package net.javafaker.junit.extension.instantitators;

import net.javafaker.junit.api.annotations.FakeData;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class InstantiatorRegistry {
    private static final List<Instantiator> BUILDER_INSTANTIATOR = List.of(new BuilderInstantiator());
    private static final List<Instantiator> REQUIRED_ARGS_CONSTRUCTOR_INSTANTIATOR = List.of(new RequiredArgsConstructorInstantiator());
    private static final List<Instantiator> DEFAULT_CONSTRUCTOR_INSTANTIATOR = List.of(new DefaultConstructorInstantiator());
    private static final List<Instantiator> ALL = Stream.of(
            BUILDER_INSTANTIATOR,
            DEFAULT_CONSTRUCTOR_INSTANTIATOR,
            REQUIRED_ARGS_CONSTRUCTOR_INSTANTIATOR)
            .flatMap(Collection::stream)
            .toList();


    public static List<Instantiator> getInstantiatorsForAnnotation(@Nullable FakeData annotation) {
        if (annotation==null)
            return ALL;
        return switch (annotation.instantiateWith()) {
            case ANY -> ALL;
            case BUILDER -> BUILDER_INSTANTIATOR;
            case REQUIRED_ARGS_CONSTRUCTOR -> REQUIRED_ARGS_CONSTRUCTOR_INSTANTIATOR;
            case DEFAULT_CONSTRUCTOR -> DEFAULT_CONSTRUCTOR_INSTANTIATOR;
        };
    }
}
