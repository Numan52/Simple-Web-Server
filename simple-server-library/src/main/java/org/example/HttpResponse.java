package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpResponse {
    private final int statusCode;
    private final Map<String, String> headers;
    private final Optional<String> body;

    public HttpResponse(int statusCode, Map<String, String> headers, Optional<String> body) {

        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Optional<String> getBody() {
        return body;
    }

    public static class Builder {
        private int statusCode;
        private Map<String, String> headers;
        private Optional<String> body;

        public Builder() {
            headers = new HashMap<>();
            this.headers.put("Date", LocalDateTime.now().toString());
            this.headers.put("Server", "My Server");
            this.body = Optional.empty();
        }

        public Builder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder addHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder setBody(String body) {
            this.body = Optional.of(body);
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(statusCode, headers, body);
        }
    }


}
