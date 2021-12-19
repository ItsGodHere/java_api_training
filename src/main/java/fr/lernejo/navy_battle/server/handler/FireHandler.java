package fr.lernejo.navy_battle.server.handler;

import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.server.utils.JsonUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class FireHandler implements CallHandler{
    @Override
    public String getAssignedPath() {
        return "/api/game/fire";
    }

    @Override
    public String[] allowedRequestMethods() {
        return new String[]{"GET"};
    }

    @Override
    public boolean isMethodAllowed(String method) {
        return Arrays.stream( this.allowedRequestMethods() ).toList().contains( method );
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String body = "<h1>404 Not Found</h1>Wrong method for request";
        if (!this.isMethodAllowed(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(404, body.length());
        }else {
            final String cell = exchange.getRequestURI().toString().split( "=" )[1];
            System.out.println("cell : " + cell);
            JsonUtil util = new JsonUtil();
            body = util.createFireRequestBody("sunk", true);
            exchange.sendResponseHeaders(202, body.length());
        }
        try(OutputStream os = exchange.getResponseBody()){
            os.write(body.getBytes());
        }
    }
}
