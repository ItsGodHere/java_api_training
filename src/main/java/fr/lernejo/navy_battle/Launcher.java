package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.server.SimpleHttpServer;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        if (args.length != 0) {
            SimpleHttpServer server = new SimpleHttpServer(args[0]);
            server.Start();
        }else {
            throw new IllegalArgumentException("Not enough arguments");
        }
    }
}
