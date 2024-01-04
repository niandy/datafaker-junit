package net.javafaker.junit.extension;

import net.javafaker.junit.api.annotations.FakeEnum;
import net.javafaker.junit.extension.data.SimpleEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DataFakerExtension.class)
public class EnumTest {

    @Test
    void testEnum(@FakeEnum SimpleEnum enumValue) {
        assertThat(enumValue)
                .isNotNull();
    }
}
