package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.server.SimpleClientServer;
import fr.lernejo.navy_battle.server.SimpleHttpServer;

import java.io.IOException;
import java.net.http.HttpResponse;

public class Launcher {
    public static void main(String[] args) throws IOException {
        if (args.length != 0) {
            SimpleHttpServer server = new SimpleHttpServer(args[0]);
            server.Start();
            if(args.length > 1) {
                SimpleClientServer client = new SimpleClientServer(args[0], args[1]);
                HttpResponse<String> response =  client.sendRequest();
            }
        }else {
            throw new IllegalArgumentException("Not enough arguments");
        }
    }
}
