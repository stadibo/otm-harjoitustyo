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
public class UserTest {

    @Test
    public void equalWhenSameUsername() {
        User user1 = new User("alpha", "beta", 0, 1, 100);
        User user2 = new User("alpha", "beta", 0, 1, 100);
        assertEquals(true, user1.equals(user2));
    }

    @Test
    public void nonEqualWhenDifferentUsername() {
        User user1 = new User("alpha", "beta", 0, 1, 100);
        User user2 = new User("prime", "even", 0, 1, 100);
        assertEquals(false, user1.equals(user2));
    }

    @Test
    public void nonEqualWhenDifferentType() {
        User user = new User("steve", "jobs", 0, 1, 100);
        Object obj = new Object();
        assertEquals(false, user.equals(obj));
    }

}
