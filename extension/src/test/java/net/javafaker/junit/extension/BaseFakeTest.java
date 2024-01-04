package net.javafaker.junit.extension;

import net.javafaker.junit.api.annotations.*;
import net.javafaker.junit.extension.DataFakerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DataFakerExtension.class)
public class BaseFakeTest {

    @Test
    void testLetterify(@FakeLetterify("start???end") String value) {
        assertThat(value)
                .isNotBlank()
                .startsWith("start")
                .endsWith("end")
                .hasSize(11)
                .matches("^start(\\w){3}end$");
    }

    @Test
    void testNumerify(@FakeNumerify("start###end") String value) {
        assertThat(value)
                .isNotBlank()
                .startsWith("start")
                .endsWith("end")
                .hasSize(11)
                .matches("^start(\\d){3}end$");
    }

    @Test
    void testBothify(@FakeBothify("start???###end") String value) {
        assertThat(value)
                .isNotBlank()
                .startsWith("start")
                .endsWith("end")
                .hasSize(14)
                .matches("^start(\\w){3}(\\d){3}end$");
    }

    @Test
    void testRegexify(@FakeRegexify("start(\\w){3}(\\d){3}end") String value) {
        assertThat(value)
                .isNotBlank()
                .startsWith("start")
                .endsWith("end")
                .hasSize(14)
                .matches("^start(\\w){3}(\\d){3}end$");
    }

    @Test
    void testTemplatify(@FakeTemplatify(value = "start%%end",
            placeholder = '%',
            options = {"First", "Second"}) String value) {
        assertThat(value)
                .isNotBlank()
                .matches("^start(First|Second){2}end$");
    }

    @Test
    void testExamplify(@FakeExamplify("abc123ABC") String value) {
        assertThat(value)
                .isNotBlank()
                .hasSize(9)
                .matches("^[a-z]{3}(\\d){3}[A-Z]{3}$");
    }

    @Test
    void testFakeChars(@FakeChars(length = 10) char[] chars) {
        assertThat(chars)
                .hasSize(10);
    }
}
