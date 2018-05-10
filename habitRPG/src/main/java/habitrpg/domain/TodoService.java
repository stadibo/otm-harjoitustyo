package habitrpg.domain;

import habitrpg.dao.TodoDao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * A class for getting and manipulating "To-do" objects by interfacing with its
 * corresponding DAO (Data access object), TodoDao.
 */
public class TodoService {

    private TodoDao todoDao;
    private List<Todo> todos;

    public TodoService(TodoDao todoDao) {
        this.todoDao = todoDao;
        this.todos = new ArrayList<>();
    }

    /**
     * Passes on a User object to the DAO (Data access object) TodoDao.
     *
     * @param user (logged in user from UserService)
     */
    public void updateUser(User user) {
        todoDao.setUser(user);
    }

    /**
     * Gets a list of "To-do" objects from TodoDao and filters them by if they
     * are completed.
     *
     * @return A list of "To-do" objects
     */
    public List<Todo> getTodosUpdate() {
        this.todos = todoDao.getAll()
                .stream()
                .filter(t -> !t.isComplete())
                .collect(Collectors.toList());
        return this.todos;
    }

    /**
     * Creates a "To-do" object and passes it on to TodoDao to be stored in the
     * database.
     *
     * @param content (name of to-do)
     * @param diff (difficulty of to-do)
     * @return if creation was successful
     */
    public boolean createTodo(String content, int diff) {
        Todo newTodo = todoDao.create(new Todo(content, diff));
        if (newTodo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Interfaces with TodoDao to set "to-do" done.
     *
     * @param key (database id)
     * @return if setting was successful
     */
    public boolean setDone(Integer key) {
        boolean success = false;
        try {
            success = todoDao.setDone(key);
        } catch (Exception e) {
        }
        return success;
    }

    /**
     * Interfaces with TodoDao to remove a to-do from the database.
     *
     * @param key (database id)
     * @return if deletion was successful
     */
    public boolean deleteTodo(Integer key) {
        boolean success = false;
        try {
            success = todoDao.delete(key);
        } catch (Exception e) {
        }
        return success;
    }

}
