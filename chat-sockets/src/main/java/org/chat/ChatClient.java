package org.chat;

import org.chat.requests.CreateRoomRequest;
import org.chat.requests.GetRoomMessagesRequest;
import org.chat.requests.SendMessageRequest;
import org.chat.responses.CreateRoomResponse;
import org.chat.responses.GetRoomMessagesResponse;
import org.chat.responses.SendMessageResponse;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    static void printOptions() {
        System.out.println("Actions list:");
        System.out.println("1) Create a room");
        System.out.println("2) Send message to a room");
        System.out.println("3) Get room messages");
        System.out.println("4) Exit room");
        System.out.print("Choose an action: ");
    }

    public static void main(String[] args) {
        final String SERVER_ADDRESS = "127.0.0.1"; // Server's IP address
        final int PORT = 12345; // Port number for the server

        try {
            Socket socket = new Socket(SERVER_ADDRESS, PORT); // Connect to the server
            System.out.println("Connected to server: " + socket);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter you username: ");

            String username = scanner.nextLine();
            while (true) {
                printOptions();
                int action = scanner.nextInt();
                switch (action) {
                    case 1:
                        System.out.print("Enter room name to be created: ");
                        scanner.nextLine();
                        String name = scanner.nextLine();
                        out.writeObject(new CreateRoomRequest(name));
                        //var r = in.readObject();
                        // System.out.println("Room created." + r.getClass());
                        var response = (CreateRoomResponse) in.readObject();
                        if (response.getCode() == 0) {
                            System.out.println("Room created.");
                        } else {
                            System.out.println("Room already exists.");
                        }

                        System.out.println();
                        break;
                    case 2:
                        System.out.print("Enter room name: ");
                        scanner.nextLine();
                        String roomName = scanner.nextLine();
                        System.out.print("Enter your message: ");
                        String content = scanner.nextLine();
                        Message message = new Message(username, content);
                        out.writeObject(new SendMessageRequest(roomName, message));
                        var resp = (SendMessageResponse) in.readObject();
                        if (resp.getCode() == 0) {
                            System.out.println("Message sent.");
                        } else {
                            System.out.println("Room doesn't exist");
                        }

                        System.out.println();
                        break;

                    case 3:

                        System.out.print("Enter room name: ");
                        scanner.nextLine();
                        String room_name = scanner.nextLine();
                        out.writeObject(new GetRoomMessagesRequest(room_name));
                        var res = (GetRoomMessagesResponse) in.readObject();
                        if (res.getCode() == 0) {
                            var messages = res.getMessages();
                            if (messages.isEmpty()) {
                                System.out.println("No messages yet.");
                            } else {
                                for (var msg : messages) {
                                    System.out.println(msg.toString());
                                }
                            }
                        } else {
                            System.out.println("Room doesn't exist");
                        }
                        System.out.println();
                        break;
                    default:
                        System.out.println("Existing...");
                        // Close the streams and the socket
                        in.close();
                        out.close();
                        socket.close();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
