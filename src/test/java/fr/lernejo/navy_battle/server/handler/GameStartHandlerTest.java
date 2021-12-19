package fr.lernejo.navy_battle.server.handler;

import fr.lernejo.navy_battle.server.SimpleHttpServer;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;


class GameStartHandlerTest {
    GameStartHandler gameStartHandlerTest = new GameStartHandler();
    @Test
    void testPingAssignedPath () {
        Assertions.assertEquals("/api/game/start", gameStartHandlerTest.getAssignedPath());
    }

    @Test
    void testMethodAllowed_with_correct_method() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .header("Content-Type", "text/plain; charset=UTF-8")
            .POST(HttpRequest.BodyPublishers.ofString("test"))
            .build();
        Assertions.assertTrue(gameStartHandlerTest.isMethodAllowed(request.method()));
    }

    @Test
    void testMethodAllowed_with_wrong_method() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .header("Content-Type", "text/plain; charset=UTF-8")
            .GET()
            .build();
        Assertions.assertFalse(gameStartHandlerTest.isMethodAllowed(request.method()));
    }

    @Test
    void testGameStartHandler_with_wrong_method() {
        try {
            SimpleHttpServer server = new SimpleHttpServer("9876");
            server.Start();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9876/api/game/start"))
                .header("Content-Type", "text/plain; charset=UTF-8")
                .GET()
                .build();
            CompletableFuture<HttpResponse<String>> completableFuture = client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response = completableFuture.join();
            server.Stop();
            org.assertj.core.api.Assertions.assertThat(response.statusCode()).isEqualTo(404);
            org.assertj.core.api.Assertions.assertThat(response.body()).isEqualTo("<h1>404 Not Found</h1>Wrong method for request");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGameStartHandler_with_wrong_contentType() {
        try {
            SimpleHttpServer server = new SimpleHttpServer("9876");
            server.Start();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9876/api/game/start"))
                .header("Content-Type", "text/plain; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString("test with wrong Content-Type"))
                .build();
            CompletableFuture<HttpResponse<String>> completableFuture = client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response = completableFuture.join();
            server.Stop();
            org.assertj.core.api.Assertions.assertThat(response.statusCode()).isEqualTo(400);
            org.assertj.core.api.Assertions.assertThat(response.body()).isEqualTo("<h1>400 Bad Content-Type</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGameStartHandler_with_wrong_data() {
        try {
            SimpleHttpServer server = new SimpleHttpServer("9876");
            server.Start();
            HttpClient client = HttpClient.newHttpClient();
            JSONObject jsonData = new JSONObject(
                new JSONTokener(new FileInputStream(
                    new File("src/test/resources/GameStartInvalid.json").getAbsolutePath())));
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9876/api/game/start"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonData.toString()))
                .build();
            CompletableFuture<HttpResponse<String>> completableFuture = client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response = completableFuture.join();
            server.Stop();
            org.assertj.core.api.Assertions.assertThat(response.statusCode()).isEqualTo(400);
            org.assertj.core.api.Assertions.assertThat(response.body()).isEqualTo("<h1>400 Bad Request</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGameStartHandler() {
        try {
            SimpleHttpServer server = new SimpleHttpServer("9876");
            server.Start();
            HttpClient client = HttpClient.newHttpClient();
            JSONObject jsonData = new JSONObject(
                new JSONTokener(new FileInputStream(
                    new File("src/test/resources/GameStartValid.json").getAbsolutePath())));
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9876/api/game/start"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonData.toString()))
                .build();
            CompletableFuture<HttpResponse<String>> completableFuture = client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response = completableFuture.join();
            server.Stop();
            org.assertj.core.api.Assertions.assertThat(response.statusCode()).isEqualTo(202);
            org.assertj.core.api.Assertions.assertThat(response.body()).contains("\"message\":\"May the fate be with you\",\"url\":\"http://localhost:9876\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
