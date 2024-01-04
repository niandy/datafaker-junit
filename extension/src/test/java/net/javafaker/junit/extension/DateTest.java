package net.javafaker.junit.extension;

import net.javafaker.junit.api.annotations.FakeBirthday;
import net.javafaker.junit.api.annotations.FakeDuration;
import net.javafaker.junit.api.annotations.FakeFuture;
import net.javafaker.junit.api.annotations.FakePast;
import net.javafaker.junit.extension.DataFakerExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(DataFakerExtension.class)
public class DateTest {

    @Test
    void testFuture(@FakeFuture(atMost = 1, unit = TimeUnit.DAYS) Timestamp timestamp,
                    @FakeFuture(atMost = 1, unit = TimeUnit.DAYS) LocalDateTime localDateTime,
                    @FakeFuture(atMost = 1, unit = TimeUnit.DAYS) LocalDate localDate,
                    @FakeFuture(atMost = 1, unit = TimeUnit.DAYS) String serialized) {
        assertAll(
                () -> assertThat(timestamp)
                        .isBetween(Instant.now(),
                                Instant.now().plus(1, ChronoUnit.DAYS)),
                () -> assertThat(localDateTime)
                        .isBetween(LocalDateTime.now(),
                                LocalDateTime.now().plusDays(1)),
                () -> assertThat(localDate)
                        .isBetween(LocalDate.now(),
                                LocalDate.now().plusDays(1)),
                () -> assertThat(serialized)
                        .isNotBlank()
                        .matches("^(\\d){4}-(\\d){2}-(\\d){2}\\s(\\d){2}:(\\d){2}:(\\d){2}$")
        );
    }

    @Test
    void testPast(@FakePast(atMost = 1, unit = TimeUnit.DAYS) Timestamp timestamp,
                    @FakePast(atMost = 1, unit = TimeUnit.DAYS) LocalDateTime localDateTime,
                    @FakePast(atMost = 1, unit = TimeUnit.DAYS) LocalDate localDate,
                    @FakePast(atMost = 1, unit = TimeUnit.DAYS) String serialized) {
        assertAll(
                () -> assertThat(timestamp)
                        .isBetween(Instant.now().minus(1, ChronoUnit.DAYS),
                                Instant.now()),
                () -> assertThat(localDateTime)
                        .isBetween(LocalDateTime.now().minusDays(1),
                                LocalDateTime.now()),
                () -> assertThat(localDate)
                        .isBetween(LocalDate.now().minusDays(1),
                                LocalDate.now()),
                () -> assertThat(serialized)
                        .isNotBlank()
                        .matches("^(\\d){4}-(\\d){2}-(\\d){2}\\s(\\d){2}:(\\d){2}:(\\d){2}$")
        );
    }

    @Test
    void testDuration(@FakeDuration(min = 1, max = 100, unit = ChronoUnit.HOURS) Duration duration) {
        assertThat(duration)
                .isBetween(Duration.ofHours(1), Duration.ofHours(100));
    }

    @Test
    void testBirthday(@FakeBirthday Timestamp timestamp,
                      @FakeBirthday LocalDateTime localDateTime,
                      @FakeBirthday LocalDate localDate,
                      @FakeBirthday String serialized) {
        assertAll(
                () -> assertThat(timestamp)
                        .isBetween(ZonedDateTime.now().minusYears(65).toInstant(),
                                ZonedDateTime.now().minusYears(18).toInstant()),
                () -> assertThat(localDateTime)
                        .isBetween(LocalDateTime.now().minusYears(65),
                                LocalDateTime.now().minusYears(18)),
                () -> assertThat(localDate)
                        .isBetween(LocalDate.now().minusYears(65),
                                LocalDate.now().minusYears(18)),
                () -> assertThat(serialized)
                        .isNotBlank()
                        .matches("^(\\d){4}-(\\d){2}-(\\d){2}\\s(\\d){2}:(\\d){2}:(\\d){2}$")
        );
    }
}
