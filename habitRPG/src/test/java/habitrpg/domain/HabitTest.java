/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class HabitTest {
    
    @Test
    public void equalWhenSameId() {
        User user = new User("username", "name",  0, 1, 100);
        Habit habit1 = new Habit(1, "stuff", false, 1, 0, user);
        Habit habit2 = new Habit(1, "stuff", false, 1, 0, user);
        assertEquals(true, habit1.equals(habit2));
    }
  
    @Test
    public void notEqualWhenDifferentId() {
        User user = new User("username", "name",  0, 1, 100);
        Habit habit1 = new Habit(1, "stuff", false, 1, 0, user);
        Habit habit2 = new Habit(2, "no stuff", false, 1, 0, user);
        assertEquals(false, habit1.equals(habit2));
    }   
    
    @Test
    public void nonEqualWhenDifferentType() {
        User user = new User("username", "name",  0, 1, 100);
        Habit habit = new Habit(1, "stuff", false, 1, 0, user);
        Object obj = new Object();
        assertEquals(false, habit.equals(obj));
    }
    
    @Test
    public void incompleteConstructorCreateCorrectHabit() {
        Habit habit1 = new Habit("stuff", 1);
        assertEquals("stuff", habit1.getContent());
        assertEquals(1, habit1.getDifficulty());
        assertEquals(0, habit1.getCurrentStreak());
        assertEquals(false, habit1.isRetired());
    }
    
}
