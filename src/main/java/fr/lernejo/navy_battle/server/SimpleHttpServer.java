package fr.lernejo.navy_battle.server;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.server.handler.CallHandler;
import fr.lernejo.navy_battle.server.handler.FireHandler;
import fr.lernejo.navy_battle.server.handler.GameStartHandler;
import fr.lernejo.navy_battle.server.handler.PingHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SimpleHttpServer {
    private final List<CallHandler> contexts = new ArrayList<>();
    private final HttpServer server;

    public SimpleHttpServer(String listening_port) throws IOException {
        int listening_port1;
        try {
            listening_port1 = Integer.parseInt(listening_port);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("The port name must be a number.");
        }
        if (listening_port1 <= 0) {
            throw new ArithmeticException("The number must be positive and greater than 0.");
        }
        this.server = HttpServer.create(new InetSocketAddress(listening_port1), 0);
    }

    public void Start () throws IOException {
        server.setExecutor(Executors.newFixedThreadPool(1));
        this.contexts.add(new PingHandler());
        this.contexts.add((new GameStartHandler()));
        this.contexts.add(new FireHandler());
        this.setupContexts();
        this.server.start();
    }

    public void Stop() {
        this.server.stop(0);
    }

    public void createContext (String path, CallHandler handler) {
        this.server.createContext(path, handler);
    }

    public void setupContexts () {
        contexts.forEach(
            context -> this.createContext(context.getAssignedPath(), context)
        );
    }
}
