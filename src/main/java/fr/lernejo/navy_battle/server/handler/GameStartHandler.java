package fr.lernejo.navy_battle.server.handler;

import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.server.utils.JsonUtil;

import java.io.*;
import java.net.URL;
import java.util.Arrays;

public class GameStartHandler implements CallHandler {

    @Override
    public String getAssignedPath() {
        return "/api/game/start";
    }

    @Override
    public String[] allowedRequestMethods() {
        return new String[]{"POST"};
    }

    @Override
    public boolean isMethodAllowed(String method) {
        return Arrays.stream(this.allowedRequestMethods()).toList().contains(method);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String body = "<h1>404 Not Found</h1>Wrong method for request";
        if (this.isMethodAllowed(exchange.getRequestMethod())) { final JsonUtil util = new JsonUtil();
            if (exchange.getRequestHeaders().get("Content-Type").toString().equals("[application/json]")) {
                if(util.schemaValidate( new String(exchange.getRequestBody().readAllBytes()) )) { body = util.createResponseBody(new URL("http://"+exchange.getRequestHeaders().getFirst("Host")), "May the fate be with you");
                    exchange.sendResponseHeaders(202, body.length()); }else { body = "<h1>400 Bad Request</h1>";
                    exchange.sendResponseHeaders(400, body.length()); }}else { body = "<h1>400 Bad Content-Type</h1>";
                exchange.sendResponseHeaders(400, body.length());
            }} else { exchange.sendResponseHeaders(404, body.length()); }
        try(OutputStream os = exchange.getResponseBody()){ os.write(body.getBytes()); }
    }}
