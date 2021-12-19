package fr.lernejo.navy_battle.server.handler;

import fr.lernejo.navy_battle.server.SimpleHttpServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;


class PingHandlerTest {
    final PingHandler pingHandlerTest = new PingHandler();

    @Test
    void testPingAssignedPath () {
        Assertions.assertEquals("/ping", pingHandlerTest.getAssignedPath());
    }

    @Test
    void testMethodAllowed_with_correct_method() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/ping"))
            .header("Content-Type", "text/plain; charset=UTF-8")
            .GET()
            .build();
        Assertions.assertTrue(pingHandlerTest.isMethodAllowed(request.method()));
    }

    @Test
    void testMethodAllowed_with_wrong_method() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/ping"))
            .header("Content-Type", "text/plain; charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofString("test"))
            .build();
        Assertions.assertFalse(pingHandlerTest.isMethodAllowed(request.method()));
    }

    @Test
    void testPingHandler() throws IOException {
        SimpleHttpServer server = new SimpleHttpServer("9222");
        server.Start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9222/ping"))
            .header("Content-Type", "text/plain; charset=UTF-8")
            .GET()
            .build();
        CompletableFuture<HttpResponse<String>> completableFuture = client
            .sendAsync(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = completableFuture.join();
        server.Stop();
        Assertions.assertEquals(response.statusCode(), 200);
        Assertions.assertEquals(response.body(), "OK");
    }

    @Test
    void testPingHandler_with_wrong_path () throws IOException {
        SimpleHttpServer server = new SimpleHttpServer("9422");
        server.Start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9422/thisIsNotTheRightPath"))
            .header("Content-Type", "text/plain; charset=UTF-8")
            .GET()
            .build();
        CompletableFuture<HttpResponse<String>> completableFuture = client
            .sendAsync(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = completableFuture.join();
        server.Stop();
        Assertions.assertEquals(response.statusCode(), 404);
        Assertions.assertEquals(response.body(), "<h1>404 Not Found</h1>No context found for request");
    }

    @Test
    void testPingHandler_with_wrong_method () throws IOException {
        SimpleHttpServer server = new SimpleHttpServer("9322");
        server.Start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9322/ping"))
            .header("Content-Type", "text/plain; charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofString("test"))
            .build();
        CompletableFuture<HttpResponse<String>> completableFuture = client
            .sendAsync(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = completableFuture.join();
        server.Stop();
        Assertions.assertEquals(response.statusCode(), 404);
        Assertions.assertEquals(response.body(), "<h1>404 Not Found</h1>Wrong method for request");
    }
}
