package org.example;

import com.sun.net.httpserver.Request;
import org.example.interfaces.RequestHandler;
import org.example.interfaces.RequestRunner;

import java.io.*;
import java.util.Map;

public class HttpHandler {
    private Map<String, RequestRunner> routes;
    private Map<RequestMethod, RequestHandler> handlers;

    public HttpHandler(Map<String, RequestRunner> routes, Map<RequestMethod, RequestHandler> handlers) {
        this.routes = routes;
        this.handlers = handlers;
    }



    public void handleConnection(InputStream inputStream, OutputStream outputStream) throws IOException {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        HttpRequest httpRequest;

        try {
            httpRequest = HttpDecoder.decode(inputStream);
        } catch (IOException e) {
            handleInvalidRequest(writer);
            return;
        }

        RequestMethod method = httpRequest.getMethod();
        RequestHandler handler = handlers.get(method);

        if (handler != null) {
            handler.handleRequest(httpRequest, outputStream, routes);
        } else {
            ResponseWriter.writeResponse(
                    writer,
                    new HttpResponse.Builder().setStatusCode(405).setBody("Unsupported request method: " + method.toString()).build()
            );
        }

    }

    public void handleInvalidRequest(Writer writer) {
        ResponseWriter.writeResponse(
                writer,
                new HttpResponse.Builder().setStatusCode(400).setBody("Incorrect request format").build()
        );
    }


}
