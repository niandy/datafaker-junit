package net.javafaker.junit.extension;

import net.javafaker.junit.extension.data.EmbeddingRecord;
import net.javafaker.junit.extension.data.SelfReferencingRecord;
import net.javafaker.junit.extension.data.SimpleRecord;
import net.javafaker.junit.api.annotations.FakeData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DataFakerExtension.class)
public class RequiredArgsConstructorTest {

    @Test
    void testSimpleRecordCreation(@FakeData SimpleRecord record) {
        assertThat(record)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void testEmbeddingRecordCreation(@FakeData EmbeddingRecord record) {
        assertThat(record)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void testSelfReferencingRecord(@FakeData SelfReferencingRecord record) {
        assertThat(record)
                .isNotNull()
                .hasFieldOrPropertyWithValue("child", null)
                .hasNoNullFieldsOrPropertiesExcept("child");
    }
}
