package net.javafaker.junit.extension;


import net.javafaker.junit.api.annotations.FakeData;
import net.javafaker.junit.extension.data.PrimitiveContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DataFakerExtension.class)
public class PrimitiveTest {
    @Test
    void testPrimitive(@FakeData PrimitiveContainer value) {
        assertThat(value)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }
}
