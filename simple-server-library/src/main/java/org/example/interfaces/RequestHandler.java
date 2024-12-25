package org.example.interfaces;

import org.example.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface RequestHandler {
    void handleRequest(HttpRequest httpRequest, OutputStream outputStream, Map<String, RequestRunner> routes) throws IOException;
}
