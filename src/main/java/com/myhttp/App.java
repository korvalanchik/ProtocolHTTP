package com.myhttp;

import java.io.IOException;
import java.net.URISyntaxException;


public class App {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, HttpStatusChecker.MyException {
        HttpStatusImageDownloader imageDownloader = new HttpStatusImageDownloader();
        imageDownloader.downloadStatusImage(100);
        imageDownloader.downloadStatusImage(102);
        imageDownloader.downloadStatusImage(200);
        imageDownloader.downloadStatusImage(302);
        imageDownloader.downloadStatusImage(500);
        imageDownloader.downloadStatusImage(404);
        imageDownloader.downloadStatusImage(600);
    }
}
