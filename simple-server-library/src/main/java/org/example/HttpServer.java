package org.example;

import org.example.interfaces.RequestHandler;
import org.example.interfaces.RequestRunner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port;
    private final Map<String, RequestRunner> routes;
    private final Map<RequestMethod, RequestHandler> handlers;
    private final Executor executor;
    private HttpHandler httpHandler;

    public HttpServer(int port) {
        this.port = port;
        this.routes = new HashMap<>();
        this.executor = Executors.newFixedThreadPool(100);
        this.handlers = Map.ofEntries(Map.entry(RequestMethod.GET, new GetRequestHandler()));
        this.httpHandler = new HttpHandler(routes, handlers);
    }

    public void start() throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                System.out.println("Waiting for connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getRemoteSocketAddress());

                executor.execute(() -> {
                    try {
                        httpHandler.handleConnection(clientSocket.getInputStream(), clientSocket.getOutputStream());
                    } catch (IOException e) {
                        System.err.println(e + ": " + e.getMessage());
                    }
                });
            }
        }

    }


    public void addRoute(RequestMethod method, String path, RequestRunner runner) {
        if (handlers.get(method) == null) {
            throw new IllegalArgumentException("Method not supported " + method);
        }

        routes.put(method.name() + path, runner);
    }


}
