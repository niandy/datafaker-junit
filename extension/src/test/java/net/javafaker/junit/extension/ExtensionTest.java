package net.javafaker.junit.extension;

import net.javafaker.junit.api.annotations.FakeData;
import net.javafaker.junit.api.annotations.FakeLetterify;
import net.javafaker.junit.api.annotations.FakeNumber;
import net.javafaker.junit.extension.data.SimpleBuilderClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(DataFakerExtension.class)
public class ExtensionTest {

    @FakeLetterify("test???")
    String stringValue;

    @FakeData
    SimpleBuilderClass classValue;

    @FakeNumber
    int intValue;

    @FakeNumber
    Float floatValue;


    @Test
    void testExtension() {
        assertAll(
                () -> assertThat(stringValue)
                        .isNotBlank()
                        .hasSize(7)
                        .isAlphabetic()
                        .startsWith("test"),
                () -> assertThat(classValue)
                        .isNotNull()
                        .hasNoNullFieldsOrProperties(),
                () -> assertThat(intValue)
                        .isNotZero(),
                () -> assertThat(floatValue)
                        .isNotNull()
                        .isNotZero()
        );
    }


}
