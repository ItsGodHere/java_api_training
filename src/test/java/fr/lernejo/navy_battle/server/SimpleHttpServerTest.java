package fr.lernejo.navy_battle.server;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class SimpleHttpServerTest {

    private SimpleHttpServer server;
    private final String port = "9999";

    @Test
    void test_negative_port () {
        String negative_port = "-9999";
        org.assertj.core.api.Assertions.assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> server = new SimpleHttpServer(negative_port));
    }

    @Test
    void test_wrong_type_port () {
        String wrong_type_port = "ThisIsNotAPort";
        org.assertj.core.api.Assertions.assertThatExceptionOfType(NumberFormatException.class)
            .isThrownBy(() -> server = new SimpleHttpServer(wrong_type_port));
    }

    @Test
    void test_correct_port () {
        org.assertj.core.api.Assertions.assertThatNoException()
            .isThrownBy(() -> server = new SimpleHttpServer(port));
    }

    @Test
    void test_server_setup () throws IOException {
        server = new SimpleHttpServer(port);
        org.assertj.core.api.Assertions.assertThatNoException()
            .isThrownBy(() -> server.Start());
        server.Stop();
    }
}
