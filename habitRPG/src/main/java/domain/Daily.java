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
public class Daily {
    
    private int id;
    private String content;
    private boolean complete;
    private int difficulty;
    private String date;
    private User user;
    
    public Daily(int id, String cont, boolean comp, int diff, String date, User user) {
        this.id = id;
        this.content = cont;
        this.complete = comp;
        this.difficulty = diff;
        this.date = date;
        this.user = user;
    }
    
    public Daily(String cont, int diff, String date, User user) {
        this.content = cont;
        this.difficulty = diff;
        this.date = date;
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

    public String getDate() {
        return date;
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
        
        Daily other = (Daily) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
