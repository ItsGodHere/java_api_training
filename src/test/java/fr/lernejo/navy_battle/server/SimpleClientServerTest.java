package fr.lernejo.navy_battle.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

class SimpleClientServerTest {
    SimpleClientServer client;

    @Test
    void test_initialisation() throws IOException {
        final SimpleHttpServer server2 = new SimpleHttpServer("9886");
        server2.Start();
        org.assertj.core.api.Assertions.assertThatNoException()
            .isThrownBy(() -> client = new SimpleClientServer("9886", "http://localhost:9876"));
        server2.Stop();
    }
    @Test
    void test_sendRequest() throws IOException {
        final SimpleHttpServer server1 = new SimpleHttpServer("9816");
        final SimpleHttpServer server2 = new SimpleHttpServer("9886");
        server1.Start();
        server2.Start();
        client = new SimpleClientServer("9886", "http://localhost:9816");
        HttpResponse<String> response = client.sendRequest();
        Assertions.assertEquals(response.statusCode(), 202);
        server1.Stop();
        server2.Stop();
    }
    @Test
    void test_wrong_url() throws IOException {
        final SimpleHttpServer server2 = new SimpleHttpServer("9566");
        server2.Start();
        org.assertj.core.api.Assertions.assertThatIllegalArgumentException()
            .isThrownBy(() -> client = new SimpleClientServer("9566", "http://wrong_url"));
        server2.Stop();
    }
    @Test
    void wrong_port() {
        org.assertj.core.api.Assertions.assertThatExceptionOfType(NumberFormatException.class)
            .isThrownBy(() -> client = new SimpleClientServer("wrong_port", "http://localhost:9876"));
        org.assertj.core.api.Assertions.assertThatExceptionOfType(ArithmeticException.class)
            .isThrownBy(() -> client = new SimpleClientServer("-9876", "http://localhost:9876"));
    }
}
