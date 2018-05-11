package habitrpg.domain;

/**
 * Class representing a habit being tracked
 */
public class Habit {
    private int id;
    private String content;
    private int difficulty;
    private boolean retired;
    private int currentStreak;
    private User user;
    
    /**
     * Constructs a Habit object when all parameters are known
     * 
     * @param id (database id)
     * @param content (description of habit)
     * @param ret (true if habit is no longer tracked, else false)
     * @param diff (difficulty of habit)
     * @param streak (how many successful days minus failed days)
     * @param user (owner of habit as a User object)
     */
    public Habit(int id, String content, boolean ret, int diff, int streak, User user) {
        this.id = id;
        this.content = content;
        this.retired = ret;
        this.difficulty = diff;
        this.currentStreak = streak;
        this.user = user;
    }
    
    /**
     * Constructs a new Habit object with default values
     * 
     * @param content (description of habit)
     * @param diff (difficulty of habit)
     */
    public Habit(String content, int diff) {
        this.content = content;
        this.retired = false;
        this.difficulty = diff;
        this.currentStreak = 0;
    }

    public boolean isRetired() {
        return retired;
    }

    public int getId() {
        return id;
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

    public User getUser() {
        return user;
    }
    
    /**
     * Compares two habits for equality. The result is true if and 
     * only if the argument is not null and is a Habit object that has the same 
     * database id as this object.
     * @param obj (the object to compare with)
     * @return true if the objects are the same; false otherwise.
     */
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
    
}