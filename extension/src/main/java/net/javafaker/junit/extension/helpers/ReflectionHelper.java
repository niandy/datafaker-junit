package net.javafaker.junit.extension.helpers;

import lombok.experimental.UtilityClass;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.platform.commons.util.ReflectionUtils.HierarchyTraversalMode.BOTTOM_UP;

@UtilityClass
public class ReflectionHelper {
    private static final int SKIP_MODIFIERS = Modifier.FINAL | Modifier.TRANSIENT | Modifier.STATIC;
    private static final Predicate<Field> WITH_MODIFIERS = field -> (field.getModifiers() & SKIP_MODIFIERS) == 0;

    public static final Predicate<Field> ANYTHING = field -> true;

    public List<Field> getFieldsForClass(Class<?> klass, Predicate<Field> predicate) {
        return ReflectionUtils.findFields(klass,
                WITH_MODIFIERS.and(predicate),
                BOTTOM_UP);
    }
}
