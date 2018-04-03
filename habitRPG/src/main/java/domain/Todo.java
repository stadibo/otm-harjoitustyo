/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author peje
 */
public class Todo {
    
    private int id;
    private String content;
    private boolean complete;
    private int difficulty;
    private User user;
    
    public Todo(int id, String content, boolean complete, int diff, User user) {
        this.id = id;
        this.content = content;
        this.complete = complete;
        this.difficulty = diff;
        this.user = user;
    }
    
    public Todo(String content, User user) {
        this.content = content;
        this.user = user;
        this.complete = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getDifficulty() {
        return difficulty;
    }
    
    public User getUser() {
        return user;
    }
    
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
        
        Todo other = (Todo) obj;
        if (this.id != other.id) {
            return false;
        }
        
        return true;
    }
    
}
