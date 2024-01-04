package net.javafaker.junit.extension;

import net.javafaker.junit.api.annotations.FakeData;
import net.javafaker.junit.extension.data.EmbeddingBuilderClass;
import net.javafaker.junit.extension.data.SimpleBuilderClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DataFakerExtension.class)
public class BuilderTest {

    @Test
    void testSimpleClass(@FakeData SimpleBuilderClass value) {
        assertThat(value)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void testEmbeddingClass(@FakeData EmbeddingBuilderClass value) {
        assertThat(value)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }
}
