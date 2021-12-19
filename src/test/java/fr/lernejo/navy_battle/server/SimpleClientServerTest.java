package fr.lernejo.navy_battle.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

class SimpleClientServerTest {
    SimpleClientServer client;
    final SimpleHttpServer server1 = new SimpleHttpServer("9876");
    final SimpleHttpServer server2 = new SimpleHttpServer("9886");
    @BeforeEach
    void setup() throws IOException {
        server1.Start();
        server2.Start();
    }
    @AfterEach
    void shutdown() {
        server1.Stop();
        server2.Stop();
    }
    @Test
    void test_initialisation() {
        org.assertj.core.api.Assertions.assertThatNoException()
            .isThrownBy(() -> client = new SimpleClientServer("9886", "http://localhost:9876"));
    }
    @Test
    void test_sendRequest() {
        client = new SimpleClientServer("9886", "http://localhost:9876");
        HttpResponse<String> response = client.sendRequest();
        Assertions.assertEquals(response.statusCode(), 202);
    }
    @Test
    void test_wrong_url() {
        org.assertj.core.api.Assertions.assertThatIllegalArgumentException()
            .isThrownBy(() -> client = new SimpleClientServer("9886", "http://wrong_url"));
    }
    @Test
    void wrong_port() {
        org.assertj.core.api.Assertions.assertThatExceptionOfType(NumberFormatException.class)
            .isThrownBy(() -> client = new SimpleClientServer("wrong_port", "http://localhost:9876"));
        org.assertj.core.api.Assertions.assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> client = new SimpleClientServer("-9876", "http://localhost:9876"));
    }
}
