package habitrpg.domain;

/**
 * Class representing a user
 */
public class User {
    
    private String name;
    private String username;
    private int experience;
    private int level;
    private int health;
    
    /**
     * Constructs a User object
     * 
     * @param username (unique identifier for user)
     * @param name ("real" name for user)
     * @param exp (experience gained at level so far)
     * @param lvl (current level)
     * @param hp (current health points)
     */
    public User(String username, String name, int exp, int lvl, int hp) {
        this.name = name;
        this.username = username;
        this.experience = exp;
        this.level = lvl;
        this.health = hp;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    
    /**
     * Compares two users for equality. The result is true if and 
     * only if the argument is not null and is a User object that has the same 
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
        
        User other = (User) obj;
        if (!this.username.equals(other.username)) {
            return false;
        }
        return true;
    }
    
    
    
}
