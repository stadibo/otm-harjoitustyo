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
    
    public TodoService(Database database) {
        this.todoDao = new TodoDao(database);
        this.database = database;
        this.todos = new ArrayList<>();
    }

    public void updateUser(User user) {
        todoDao.setUser(user);
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
    
    public boolean setDoneGui(Integer key) {
        boolean success = false;
        try {
            success = todoDao.setDone(key);
        } catch (Exception e) {
        }
        return success;
    }
    
    public boolean deleteTodoGui(Integer key) {
        boolean success = false;
        try {
            success = todoDao.delete(key);
        } catch (Exception e) {
        }
        return success;
    }

}
