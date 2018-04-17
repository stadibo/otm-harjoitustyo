/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.domain;

import java.util.Date;

/**
 *
 * @author peje
 */
public class Habit {
    private int id;
    private String content;
    private int difficulty;
    private boolean retired; 
    //private Integer type; // 1 = to cultivate (good), 0 = to break (bad)
    private int currentStreak;
    private User user;
    
    public Habit(int id, String content, boolean ret, int diff, int streak, User user) {
        this.id = id;
        this.content = content;
        this.retired = ret;
        this.difficulty = diff;
        this.currentStreak = streak;
        this.user = user;
    }
    
    public Habit(String content, int diff) {
        this.content = content;
        this.retired = false;
        this.difficulty = diff;
        this.currentStreak = 0;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
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

    public int getDifficulty() {
        return difficulty;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
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
        
        Habit other = (Habit) obj;
        if (this.id != other.id) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "Habit: " + content + "\n\tdifficulty: " + difficulty + "\n\tcurrentStreak: " + currentStreak;
    }
    
    
    
}
