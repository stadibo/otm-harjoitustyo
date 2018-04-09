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
public class UserTest {
    
    @Test
    public void equalWhenSameUsername() {
        User user1 = new User("alpha", "beta", "delta");
        User user2 = new User("alpha", "beta", "delta");
        assertEquals(true, user1.equals(user2));
    }
    
    @Test
    public void nonEqualWhenDifferentUsername() {
        User user1 = new User("alpha", "beta", "delta");
        User user2 = new User("prime", "even", "odd");
        assertEquals(false, user1.equals(user2));
    } 
    
    @Test
    public void nonEqualWhenDifferentType() {
        User user = new User("steve", "jobs", "think different");
        Object obj = new Object();
        assertEquals(false, user.equals(obj));
    }
    
}
