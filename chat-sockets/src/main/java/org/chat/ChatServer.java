package org.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {
    public static void main(String[] args) {
        final int PORT = 12345; // Port number for the server
        ConcurrentHashMap<String, ArrayList<Message>> rooms = new ConcurrentHashMap();
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket,rooms);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
