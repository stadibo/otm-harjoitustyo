package habitrpg.domain;

/**
 *
 * @author peje
 */
public class User {
    
    private String name;
    private String username;
    private int experience;
    private int level;
    private int health;
    
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
