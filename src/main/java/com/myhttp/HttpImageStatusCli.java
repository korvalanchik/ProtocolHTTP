package com.myhttp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpImageStatusCli {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        new HttpImageStatusCli().askStatus();
    }
    void askStatus() throws URISyntaxException, IOException, InterruptedException {
        String urlString = "http://localhost:8080/test";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .GET()
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/jpg")
                .build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int responseCode = response.statusCode();
        if(responseCode == 200) {
            System.out.println("All good");
        }
    }

}
