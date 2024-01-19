package com.myhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class HttpImageStatusCli {
    private static final String EXIT_MESSAGE = "0";
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        new HttpImageStatusCli().askStatus();
    }
    void askStatus() throws IOException, URISyntaxException, InterruptedException {
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        String inputMessage = null;
        HttpStatusImageDownloader image = new HttpStatusImageDownloader();
        while (true) {
            System.out.print("Enter HTTP status code(000-999) or 0 to quit: ");
            inputMessage = consoleIn.readLine();
            if (EXIT_MESSAGE.equals(inputMessage)) {
                break;
            }
            if (!Pattern.matches("\\d\\d\\d",inputMessage)) {
                System.out.println("Please enter valid number");
            } else {
                try {
                    image.downloadStatusImage(Integer.parseInt(inputMessage));
                } catch (HttpStatusChecker.MyException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
