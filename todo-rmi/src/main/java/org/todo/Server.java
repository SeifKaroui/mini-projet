package org.todo;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try {
            TodoManager todoManager = new TodoManager();
            Registry registry = LocateRegistry.createRegistry(1099);

            registry.bind("TodoManager",todoManager);

            System.out.println("Server ready: ");

            // keep app running
            Scanner scanner = new Scanner(System.in);
            scanner.nextInt();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
