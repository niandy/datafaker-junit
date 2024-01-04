package net.javafaker.junit.extension.data;

import net.javafaker.junit.api.annotations.FakeData;
import net.javafaker.junit.extension.DataFakerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DataFakerExtension.class)
public class DefaultConstructorTest {
    @Test
    void testSimpleClass(@FakeData SimpleClass value) {
        assertThat(value)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void testEmbeddingClass(@FakeData EmbeddingClass value) {
        assertThat(value)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }
}
