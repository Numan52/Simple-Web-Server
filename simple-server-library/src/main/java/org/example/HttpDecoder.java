package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class HttpDecoder {

    public HttpDecoder() {
    }

    public static HttpRequest decode(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        HttpRequest request = null;

        try {
            List<String> lines = readRequest(reader);
            System.out.println("request lines: " + lines);
            request = buildRequest(lines);
        } catch (IOException e) {
            System.err.println("Error reading request" +  e.getMessage());
            throw new IOException("Invalid HTTP Request" +  e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error parsing request" +  e.getMessage());
            throw new IOException("Invalid HTTP Request" +  e.getMessage());
        }
        return request;
    }


    public static List<String> readRequest(BufferedReader reader) throws IOException {
        List<String> lines= new ArrayList<>();

        String line;
        System.out.println("READING HTTP REQUEST");

        while ((line = reader.readLine()) != null) {

            if (line.isEmpty()) {
                break;
            }
            System.out.println("READING HTTP REQUEST");
            System.out.println(line);
            lines.add(line);
        }


        return lines;
    }

    public static HttpRequest buildRequest(List<String> requestLines) throws IOException {
        String[] firstLine = requestLines.get(0).split(" ");
        if (firstLine.length < 2) {
            throw new IllegalArgumentException("Request must contain the method and path");
        }
        RequestMethod method = RequestMethod.valueOf(firstLine[0]);
        String uri = firstLine[1];
        Map<String, String> headers = new HashMap<>();

        // Headers
        for (int i = 1; i < requestLines.size(); i++) {
            String line = requestLines.get(i);

            String[] header = line.split(":", 2);
            if (header.length != 2) {
                throw new IllegalArgumentException("Incorrect header format: " + line);
            }
            headers.put(header[0].trim(), header[1].trim());
        }

        return new HttpRequest.Builder()
                .setMethod(method)
                .setUrl(uri)
                .setHeaders(headers)
                .build();
    }


}
