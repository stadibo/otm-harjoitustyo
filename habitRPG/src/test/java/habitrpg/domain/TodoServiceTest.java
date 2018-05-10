/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import habitrpg.dao.Database;
import habitrpg.dao.TodoDao;
import java.io.File;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class TodoServiceTest {

    private TodoService ts;
    private TodoDao todoDao;

    public TodoServiceTest() {

    }

    @Before
    public void setUp() {
        Database db = new Database();
        db.createDatabase("test.db");
        todoDao = new TodoDao(db);
        ts = new TodoService(todoDao);

        User user = new User("tester", "elon musk", 0, 1, 100);
        ts.updateUser(user);

        todoDao.setUser(user);
        todoDao.create(new Todo("Run", 1));
    }

    @After
    public void tearDown() {
        File file = new File("test.db");
        file.delete();
    }

    @Test
    public void canCreateNewTodoSuccess() {
        assertTrue(ts.createTodo("Fight", 2));
        assertEquals("Fight", todoDao.getOne(2).getContent());
    }

    @Test
    public void getTodosUpdateSuccess() {
        List<Todo> todos = ts.getTodosUpdate();
        assertEquals(1, todos.get(0).getId());
        assertEquals("Run", todos.get(0).getContent());
        assertEquals(1, todos.get(0).getDifficulty());
        assertEquals(false, todos.get(0).isComplete());
        assertEquals("tester", todos.get(0).getUser().getUsername());
    }

    @Test
    public void canMarkTodoAsComplete() {
        assertTrue(ts.setDone(1));
        assertTrue(todoDao.getOne(1).isComplete());
    }

    @Test
    public void canDeleteTodo() {
        todoDao.create(new Todo("Walk", 3));
        assertEquals("Walk", todoDao.getOne(2).getContent());
        assertTrue(ts.deleteTodo(2));
        assertEquals(null, todoDao.getOne(2));
    }

}
