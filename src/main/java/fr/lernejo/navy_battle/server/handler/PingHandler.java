package fr.lernejo.navy_battle.server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class PingHandler implements CallHandler {
        @Override
        public String getAssignedPath() {
            return "/ping";
        }

        @Override
        public String[] allowedRequestMethods() {
            return new String[]{"GET"};
        }

        @Override
        public boolean isMethodAllowed(String method) {
            return Arrays.stream(this.allowedRequestMethods()).toList().contains(method);
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String body = "<h1>404 Not Found</h1>Wrong method for request";
            if (this.isMethodAllowed(exchange.getRequestMethod())) {
                body = "OK";
                exchange.sendResponseHeaders(200, body.length());
            } else {
                exchange.sendResponseHeaders(404, body.length());
            }
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body.getBytes());
            }
        }
    }
