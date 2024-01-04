package net.javafaker.junit.extension;

import net.javafaker.junit.api.annotations.FakeCollection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DataFakerExtension.class)
public class TestCollection {

    @Test
    void testArray(@FakeCollection(componentType = String.class) String[] values) {
        assertThat(values)
                .hasSizeBetween(1, 5)
                .doesNotContainNull();
    }

    @Test
    void testCollection(@FakeCollection(componentType = String.class) ArrayList<String> values) {
        assertThat(values)
                .hasSizeBetween(1, 5)
                .hasOnlyElementsOfType(String.class)
                .doesNotContainNull();
    }
}
