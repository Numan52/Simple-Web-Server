package org.example.interfaces;

import org.example.HttpRequest;
import org.example.HttpResponse;

public interface RequestRunner {
    public HttpResponse run(HttpRequest request);
}
