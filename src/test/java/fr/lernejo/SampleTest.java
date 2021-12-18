package fr.lernejo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SampleTest {

    private final Sample sample = new Sample();

    @Test
    void op_add() {
        int a = 2, b = 3;
        int result = sample.op( Sample.Operation.ADD, a, b);
        Assertions.assertThat(result).as("ADD 2 to 3")
            .isEqualTo(5);
    }

    @Test
    void op_mult() {
        int a = 2, b = 3;
        int result = sample.op( Sample.Operation.MULT, a, b);
        Assertions.assertThat(result).as("MULT 2 to 3")
            .isEqualTo(6);
    }

    @Test
    void fact_of_zero() {
        int n = 0;
        int result = sample.fact(n);
        Assertions.assertThat(result).as("Fact of 0")
            .isEqualTo(1);
    }

    @Test
    void fact_of_negative_number() {
        int n = -3;
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> sample.fact(n));
    }

    @Test
    void fact() {
        int n = 3;
        int result = sample.fact(n);
        Assertions.assertThat(result).as("Fact of 3")
            .isEqualTo(6);
    }
}
