package org.todo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Client {
    static void printOptions() {
        System.out.println("Actions list:");
        System.out.println("1) Get todo list");
        System.out.println("2) Add todo");
        System.out.println("3) Delete todo");
        System.out.println("4) Exit");
        System.out.print("Choose an action: ");
    }

    public static void main(String[] args) {
        try {
            TodoManagerInterface todoManager = (TodoManagerInterface) Naming.lookup("//localhost/TodoManager");
            System.out.println("Client connected to server");

            Scanner scanner = new Scanner(System.in);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

            while (true) {
                printOptions();

                int action = scanner.nextInt();
                switch (action) {
                    // Get todo list
                    case 1:
                        var todos = todoManager.getTodos();
                        if (!todos.isEmpty()) {
                            for (int i = 0; i < todos.size(); i++) {

                                var time = formatter.format(todos.get(i).getCreationTime());
                                System.out.println("Index:  " + i + "   Creation time: " + time);
                                System.out.println(todos.get(i).getContent() );
                                System.out.println();
                            }
                        } else {
                            System.out.println("Todo list is currently empty.");
                        }
                        System.out.println();
                        break;

                    // Add todo
                    case 2:

                        System.out.println("Write your todo (Press Enter twice to stop): ");

                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null && !line.isEmpty()) {
                            content.append(line);
                            content.append('\n');
                        }
                        content.deleteCharAt(content.length() - 1);
                        var todo = new Todo(content.toString());
                        todoManager.addTodo(todo);
                        System.out.println("Todo added");
                        break;

                    // Delete todo
                    case 3:
                        System.out.print("Enter index of todo to be deleted: ");
                        int index = scanner.nextInt();

                        var ok = todoManager.deleteTodo(index);
                        if (!ok) {
                            System.out.println("Index specified does not exist");
                        }
                        break;
                    default:
                        scanner.close();
                        System.out.println("Exiting");
                        System.exit(-1);
                        break;
                }

            }
        } catch (Exception e) {
            System.err.println("Client Error: " + e.getMessage());
            e.printStackTrace();

        }
    }

}
