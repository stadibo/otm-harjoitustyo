/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import habitrpg.dao.Database;
import habitrpg.dao.TodoDao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author peje
 */
public class TodoService {

    private TodoDao todoDao;
    private Database database;
    private List<Todo> todos;

    public TodoService(Database database, User user) {
        this.todoDao = new TodoDao(database, user);
        this.database = database;
        this.todos = new ArrayList<>();
    }

    public void updateUser(User user) {
        todoDao.setUser(user);
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public List<Todo> getTodosUpdate() {
        this.todos = todoDao.getAll()
                .stream()
                .filter(t -> !t.isComplete())
                .collect(Collectors.toList());
        return this.todos;
    }

    public boolean createTodo(String content, int diff) {
        Todo newTodo = todoDao.create(new Todo(content, diff));
        if (newTodo == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean setDone(Integer key) {
        boolean success = false;
        try {
            success = todoDao.setDone(todos.get(key - 1).getId());
        } catch (Exception e) {
        }
        return success;
    }

    public boolean deleteTodo(Integer key) {
        boolean success = false;
        try {
            success = todoDao.delete(todos.get(key - 1).getId());
        } catch (Exception e) {
        }
        return success;
    }

}