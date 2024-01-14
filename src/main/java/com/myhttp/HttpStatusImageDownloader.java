package com.myhttp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpStatusImageDownloader {
    void downloadStatusImage(int code) throws URISyntaxException, HttpStatusChecker.MyException, IOException, InterruptedException {
        String urlString = HttpStatusChecker.getStatusImage(code);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .GET()
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/jpg")
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        String pathname = String.format("images\\%d.jpg", code);
        if(!Files.exists(Paths.get(".\\images"))) {
            Files.createDirectory(Paths.get(".\\images"));
        }
        try {
            Files.copy(response.body(), new File(pathname).toPath());
        } catch (FileAlreadyExistsException e) {
            System.out.println("File already exists");
        }
        finally {
            response.body().close();
        }
    }
}
