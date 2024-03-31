package org.todo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class TodoManager extends UnicastRemoteObject implements TodoManagerInterface {

    private final ArrayList<Todo> todos;

    protected TodoManager() throws RemoteException {
        super();
        this.todos = new ArrayList<>();
    }


    @Override
    public ArrayList<Todo> getTodos() throws RemoteException {
        return todos;
    }

    @Override
    public void addTodo(Todo todo) throws RemoteException {
        todos.add(todo);
    }

    @Override
    public boolean deleteTodo(int index) throws RemoteException {
        if (index >= 0 && index < todos.size()) {
            todos.remove(index);
            return true;
        }
        return false;
    }
}
