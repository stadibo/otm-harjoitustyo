package habitrpg.domain;

/**
 * Class representing a to-do
 * 
 */
public class Todo {
    
    private int id;
    private String content;
    private boolean complete;
    private int difficulty;
    private User user;
    
    /**
     * Constructs a Todo object when all parameters are known
     * 
     * @param id (database id)
     * @param content (description of to-do)
     * @param complete (true if to-do is done, else false)
     * @param diff (difficulty of to-do)
     * @param user (owner of to-do as a User object)
     */
    public Todo(int id, String content, boolean complete, int diff, User user) {
        this.id = id;
        this.content = content;
        this.complete = complete;
        this.difficulty = diff;
        this.user = user;
    }
    
    /**
     * Constructs a new Todo object with default values
     * 
     * @param content (description of to-do)
     * @param diff (difficulty of to-do)
     */
    public Todo(String content, int diff) {
        this.content = content;
        this.difficulty = diff;
        this.complete = false;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isComplete() {
        return complete;
    }

    public int getDifficulty() {
        return difficulty;
    }
    
    public User getUser() {
        return user;
    }
    
    /**
     * Compares two to-do:s for equality. The result is true if and 
     * only if the argument is not null and is a Todo object that has the same 
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
        
        Todo other = (Todo) obj;
        if (this.id != other.id) {
            return false;
        }
        
        return true;
    }
    
}
