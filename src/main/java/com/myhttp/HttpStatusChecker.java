package com.myhttp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpStatusChecker {
    public static String getStatusImage(int code) throws IOException, URISyntaxException, InterruptedException, MyException {
        String urlString = "https://http.cat/" + code + ".jpg";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .GET()
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/jpg")
                .build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int responseCode = response.statusCode();
        if(responseCode == 404) {
            throw new MyException(code);
        }
        return urlString;
    }
    static class MyException extends Exception {
        public MyException(int code) {
            super("There is not image for HTTP status: " + code);
        }
    }
}
