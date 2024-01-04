package net.javafaker.junit.extension;

import net.javafaker.junit.api.annotations.FakeBytes;
import net.javafaker.junit.api.annotations.FakeDouble;
import net.javafaker.junit.api.annotations.FakeInteger;
import net.javafaker.junit.api.annotations.FakeNumber;
import net.javafaker.junit.extension.DataFakerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DataFakerExtension.class)
public class FakeNumberTest {

    @Test
    void testBytes(@FakeBytes(length = 10) byte[] value) {
        assertThat(value)
                .hasSize(10);
    }

    @Test
    void testInteger(@FakeInteger(min = 1000, max = 1500) Long value) {
        assertThat(value)
                .isBetween(1000L, 1500L);
    }

    @Test
    void testDouble(@FakeDouble(min = -1.0d, max = 1.0d) Double value) {
        assertThat(value)
                .isBetween(-1.0d, 1.0d);
    }

    @Test
    void testNumber(@FakeNumber Integer value) {
        assertThat(value)
                .isNotZero();
    }
}
