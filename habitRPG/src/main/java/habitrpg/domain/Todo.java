package habitrpg.domain;

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
