/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

/**
 *
 * @author peje
 */
public class Daily {
    
    private int id;
    private String content;
    private boolean complete;
    private boolean retired;
    private int difficulty;
    private String date;
    private boolean[] daysShown;
    private User user;
    
    public Daily(int id, String cont, boolean comp, boolean ret, int diff, String date, User user) {
        this.id = id;
        this.content = cont;
        this.complete = comp;
        this.retired = ret;
        this.difficulty = diff;
        this.date = date;
        this.user = user;
    }
    
    public Daily(String cont, int diff, String date) {
        this.content = cont;
        this.difficulty = diff;
        this.date = date;
        this.complete = false;
        this.retired = false;
    }

    public boolean isRetired() {
        return retired;
    }

    public boolean[] getDaysShown() {
        return daysShown;
    }

    public void setDaysShown(boolean[] daysShown) {
        this.daysShown = daysShown;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public void setDate(String date) {
        this.date = date;
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
