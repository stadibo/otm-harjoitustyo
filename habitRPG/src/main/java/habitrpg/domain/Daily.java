package habitrpg.domain;

/**
 * Class representing a daily task
 *
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
    
    /**
     * Constructs a Daily object when all parameters are known
     * 
     * @param id (database id)
     * @param cont (description of daily task)
     * @param comp (true if daily task is done, else false)
     * @param ret (true if daily task is no longer tracked, else false)
     * @param diff (difficulty of daily task)
     * @param date (date when daily task was last shown)
     * @param user (owner of daily task as a User object)
     */
    public Daily(int id, String cont, boolean comp, boolean ret, int diff, String date, User user) {
        this.id = id;
        this.content = cont;
        this.complete = comp;
        this.retired = ret;
        this.difficulty = diff;
        this.date = date;
        this.user = user;
        this.daysShown = null;
    }
    
    /**
     * Constructs a new Daily object with default values
     * 
     * @param cont (description of daily task)
     * @param diff (difficulty of daily task)
     * @param date (date when daily task was created)
     */
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

    /**
     * Compares two daily tasks for equality. The result is true if and 
     * only if the argument is not null and is a Daily object that has the same 
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
        
        Daily other = (Daily) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
