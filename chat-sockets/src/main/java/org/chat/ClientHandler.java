package org.chat;

import org.chat.requests.CreateRoomRequest;
import org.chat.requests.GetRoomMessagesRequest;
import org.chat.requests.SendMessageRequest;
import org.chat.responses.CreateRoomResponse;
import org.chat.responses.GetRoomMessagesResponse;
import org.chat.responses.SendMessageResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

class ClientHandler implements Runnable {
    final private Socket clientSocket;
    final private ConcurrentHashMap<String, ArrayList<Message>> rooms;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket, ConcurrentHashMap<String, ArrayList<Message>> rooms) {
        this.clientSocket = socket;
        this.rooms = rooms;
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {

                Object req = in.readObject();
                String roomName;

                switch (req) {
                    case CreateRoomRequest request -> {
                        roomName = request.getRoomName();
                        if (rooms.containsKey(roomName)) {
                            out.writeObject(new CreateRoomResponse(-1));
                        } else {
                            rooms.put(roomName, new ArrayList<Message>());
                            out.writeObject(new CreateRoomResponse(0));
                        }
                    }
                    case GetRoomMessagesRequest request -> {

                        roomName = request.getRoomName();

                        if (rooms.containsKey(roomName)) {
                            out.writeObject(new GetRoomMessagesResponse(0, rooms.get(roomName)));
                        } else {
                            out.writeObject(new GetRoomMessagesResponse(-1, new ArrayList<>()));
                        }
                    }
                    case SendMessageRequest request -> {

                        roomName = request.getRoomName();
                        var message = request.getMessage();
                        if (rooms.containsKey(roomName)) {
                            rooms.get(roomName).add(message);
                            out.writeObject(new SendMessageResponse(0));
                        } else {
                            out.writeObject(new SendMessageResponse(-1));
                        }
                    }
                    case null, default -> {
                        System.out.println("Error parsing request.");
                        System.exit(-1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}