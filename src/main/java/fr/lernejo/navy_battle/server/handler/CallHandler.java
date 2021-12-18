package fr.lernejo.navy_battle.server.handler;

import com.sun.net.httpserver.HttpHandler;

public interface CallHandler extends HttpHandler {
    String getAssignedPath();
    String[] allowedRequestMethods();
    boolean isMethodAllowed(String method);
}
