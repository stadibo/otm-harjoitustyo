/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class TodoTest {
    
    @Test
    public void equalWhenSameId() {
        User user = new User("username", "name", "motto");
        Todo todo1 = new Todo(1, "stuff", false, 1, user);
        Todo todo2 = new Todo(1, "stuff", false, 1, user);
        assertEquals(true, todo1.equals(todo2));
    }
  
    @Test
    public void notEqualWhenDifferentId() {
        User user = new User("username", "name", "motto");
        Todo todo1 = new Todo(1, "stuff", false, 1, user);
        Todo todo2 = new Todo(2, "no stuff", false, 1, user);
        assertEquals(false, todo1.equals(todo2));
    }   
    
    @Test
    public void nonEqualWhenDifferentType() {
        User user = new User("username", "name", "motto");
        Todo todo = new Todo(1, "stuff", false, 1, user);
        Object obj = new Object();
        assertEquals(false, todo.equals(obj));
    }
    
}
