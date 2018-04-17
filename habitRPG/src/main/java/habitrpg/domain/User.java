/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import java.util.Objects;

/**
 *
 * @author peje
 */
public class User {
    
    private String name;
    private String username;
    private String motto; //inspirational quote to be displayed when you login.
    
    public User(String username, String name, String motto) {
        this.name = name;
        this.username = username;
        this.motto = motto;
    }
    
//    public User(String username, String name) {
//        this.name = name;
//        this.username = username;
//    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
    
    public String getMotto() {
        return motto;
    }

//    public void setMotto(String motto) {
//        this.motto = motto;
//    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        User other = (User) obj;
        if (!this.username.equals(other.username)) {
            return false;
        }
        return true;
    }
    
    
    
}
