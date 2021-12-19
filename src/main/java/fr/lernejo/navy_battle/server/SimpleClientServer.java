package fr.lernejo.navy_battle.server;

import fr.lernejo.navy_battle.server.utils.UrlUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.IllegalCharsetNameException;
import java.util.concurrent.CompletableFuture;

public class SimpleClientServer {
    private final int listening_port;
    private final HttpClient client;
    private final String url;

    public SimpleClientServer(String listening_port, String url) {
        try {
            this.listening_port = Integer.parseInt(listening_port);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("The port name must be a number.");
        }
        if (this.listening_port <= 0) {
            throw new ArithmeticException("The number must be positive and greater than 0.");
        }
        if(!UrlUtil.useRegex(url)){
            throw new IllegalCharsetNameException("Wrong url");
        }
        this.url = url;
        this.client = HttpClient.newHttpClient();
    }

    public HttpRequest makeRequest() {
        return HttpRequest.newBuilder()
            .uri(URI.create(this.url + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(
                "{\"id\":\"1\", \"url\":\"http://localhost:" + this.listening_port + "\", \"message\":\"hello\"}"))
            .build();
    }

    public HttpResponse<String> sendRequest() {
        final CompletableFuture<HttpResponse<String>> completableFuture = client
            .sendAsync(this.makeRequest(), HttpResponse.BodyHandlers.ofString());
        return completableFuture.join();
    }
}
