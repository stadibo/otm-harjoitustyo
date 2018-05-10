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
public class DailyTest {
    
    @Test
    public void equalWhenSameId() {
        User user = new User("username", "name",  0, 1, 100);
        Daily d1 = new Daily(1, "stuff", false, false, 1, "20180101", user);
        Daily d2 = new Daily(1, "stuff", false, false, 1, "20180101", user);
        assertEquals(true, d1.equals(d2));
    }
  
    @Test
    public void notEqualWhenDifferentId() {
        User user = new User("username", "name",  0, 1, 100);
        Daily d1 = new Daily(1, "stuff", false, false, 1, "20180101", user);
        Daily d2 = new Daily(2, "no stuff", false, false, 1, "20180101", user);
        assertEquals(false, d1.equals(d2));
    }   
    
    @Test
    public void nonEqualWhenDifferentType() {
        User user = new User("username", "name",  0, 1, 100);
        Daily daily = new Daily(1, "stuff", false, false, 1, "20180101", user);
        Object obj = new Object();
        assertEquals(false, daily.equals(obj));
    }
    
    @Test
    public void incompleteConstructorCreateCorrectDaily() {
        Daily daily1 = new Daily("stuff", 1, "20180101");
        assertEquals("stuff", daily1.getContent());
        assertEquals(1, daily1.getDifficulty());
        assertEquals(false, daily1.isComplete());
        assertEquals(false, daily1.isRetired());
        assertEquals("20180101", daily1.getDate());
    }
    
}
