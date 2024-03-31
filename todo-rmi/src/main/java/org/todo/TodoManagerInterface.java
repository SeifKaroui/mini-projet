package org.todo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface TodoManagerInterface extends Remote {
    ArrayList<Todo> getTodos() throws RemoteException;

    void addTodo(Todo todo) throws RemoteException;

    boolean deleteTodo(int index) throws RemoteException;
}
