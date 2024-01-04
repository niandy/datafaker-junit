package net.javafaker.junit.extension;

import net.javafaker.junit.api.annotations.FakeProvided;
import net.javafaker.junit.api.enums.ProvidedBy;
import net.javafaker.junit.extension.DataFakerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(DataFakerExtension.class)
public class FakeProvidedTest {

    @Test
    void testProvidedByMethod(@FakeProvided(name = "address", key = "streetName") String address) {
        assertThat(address)
                .isNotBlank();
    }

    @Test
    void testProvidedByClassName(@FakeProvided(providedBy = ProvidedBy.CLASSNAME,
            name = "net.datafaker.providers.base.Address",
            key = "streetName") String address) {
        assertThat(address)
                .isNotBlank();
    }

}
