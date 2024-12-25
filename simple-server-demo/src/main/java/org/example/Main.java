package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        HttpServer server = new HttpServer(8080);
        server.addRoute(RequestMethod.GET, "/hello",
                (req) -> new HttpResponse.Builder()
                        .setStatusCode(200)
                        .addHeader("Content-Type", "text/html")
                        .setBody("<HTML> <h1> Hello World </h1> </HTML> ")
                        .build()
        );
        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}