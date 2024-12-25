package org.example;

import java.util.Map;

public class HttpRequest {
    private final RequestMethod method;
    private final String url;
    private final Map<String, String> headers;

    public HttpRequest(RequestMethod method, String url, Map<String, String> headers) {
        this.method = method;
        this.url = url;
        this.headers = headers;
    }


    public RequestMethod getMethod() {
        return method;
    }

    public String getUrl() {

        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }


    public static class Builder{
        private RequestMethod method;
        private String url;
        private Map<String, String> headers;

        public Builder() {
        }

        public Builder setMethod(RequestMethod method) {
            this.method = method;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public HttpRequest build(){
            return new HttpRequest(method, url, headers);
        }
    }
}
