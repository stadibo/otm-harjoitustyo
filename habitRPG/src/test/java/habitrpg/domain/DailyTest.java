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
        User user = new User("username", "name", "motto");
        Daily d1 = new Daily(1, "stuff", false, 1, "today", user);
        Daily d2 = new Daily(1, "stuff", false, 1, "today", user);
        assertEquals(true, d1.equals(d2));
    }
  
    @Test
    public void notEqualWhenDifferentId() {
        User user = new User("username", "name", "motto");
        Daily d1 = new Daily(1, "stuff", false, 1, "today", user);
        Daily d2 = new Daily(2, "no stuff", false, 1, "today", user);
        assertEquals(false, d1.equals(d2));
    }   
    
    @Test
    public void nonEqualWhenDifferentType() {
        User user = new User("username", "name", "motto");
        Daily daily = new Daily(1, "stuff", false, 1, "today", user);
        Object obj = new Object();
        assertEquals(false, daily.equals(obj));
    }
    
}
