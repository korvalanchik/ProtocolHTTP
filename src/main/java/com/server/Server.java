package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ExecutorService pool;
    private static final String END_OF_MESSAGE_MARK = "\n";

    public static void main(String[] args) {
        final int MAX_CONNECTIONS = 10;
        final int PORT = 9999;

        startServer(MAX_CONNECTIONS, PORT);
    }

    private static void startServer(int maxConnections, int port) {
        pool = Executors.newFixedThreadPool(maxConnections);
//        BufferedReader in = null;
//        OutputStream out = null;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port: " + port);

            while (true) {
                listenForClients(serverSocket);
                System.out.println("Listening");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void listenForClients(ServerSocket serverSocket) {
        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            pool.submit(() -> handleClient(serverSocket, clientSocket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(ServerSocket serverSocket, Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream out = clientSocket.getOutputStream()
        ) {
            String receivedMessage = null;

//            while(!receivedMessage.equals("Q")) {
                receivedMessage = in.readLine();
                if (receivedMessage != null) {
                    System.out.println("Client request message: " + receivedMessage);

                    String serverResponse = receivedMessage.toUpperCase() + END_OF_MESSAGE_MARK;

                    out.write(serverResponse.getBytes());
                    out.flush();
                }
//                clientSocket = serverSocket.accept();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
