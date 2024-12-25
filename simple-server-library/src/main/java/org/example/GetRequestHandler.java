package org.example;

import org.example.interfaces.RequestHandler;
import org.example.interfaces.RequestRunner;

import java.io.*;
import java.util.Map;
import java.util.Optional;

public class GetRequestHandler implements RequestHandler {
    @Override
    public void handleRequest(HttpRequest httpRequest, OutputStream outputStream, Map<String, RequestRunner> routes) throws IOException {


        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            RequestRunner requestRunner = routes.get(httpRequest.getMethod() + httpRequest.getUrl());

            if (requestRunner != null) {
                ResponseWriter.writeResponse(writer, requestRunner.run(httpRequest));
            } else {
                ResponseWriter.writeResponse(
                        writer,
                        new HttpResponse.Builder().setStatusCode(404).setBody("Resource not found").build()
                );
            }

        }


    }




}
