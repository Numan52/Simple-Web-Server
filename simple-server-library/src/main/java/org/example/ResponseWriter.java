package org.example;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ResponseWriter {
    public static void writeResponse(Writer bufferedWriter, HttpResponse response) {
        try {
            int statusCode = response.getStatusCode();
            String statusMessage = HttpStatusCode.STATUS_CODES.get(statusCode);

            bufferedWriter.write("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n");
            System.out.println("dasdadsasda");
            for (String header : getHeaders(response)) {
                bufferedWriter.write(header);
            }
            bufferedWriter.write("\r\n");
            if (response.getBody().isPresent()) {
                bufferedWriter.write(response.getBody().get());
            }

        } catch (IOException e) {
            System.err.println("Error writing HTTP response: " + e.getMessage());
            try {
                bufferedWriter.write("HTTP/1.1 500 Internal Server Error\r\n");
                bufferedWriter.write("Content-Length: 0\r\n");
                bufferedWriter.write("\r\n");
            } catch (IOException ex) {
                System.err.println("Failed to send fallback response: " + ex.getMessage());
            }
        }
    }

    private static List<String> getHeaders(HttpResponse response) {
        List<String> headers = new ArrayList<>();
        // include default headers
        for (Map.Entry<String, String> entry : response.getHeaders().entrySet()) {
            headers.add(entry.getKey() + ": " + entry.getValue()  + "\r\n") ;
        }

        try {
            if (response.getBody().isPresent()) {
                headers.add("Content-Length: " + response.getBody().get().getBytes("UTF-8").length + "\r\n") ;
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


        return headers;

    }



}
