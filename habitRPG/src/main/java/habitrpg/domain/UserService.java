package habitrpg.domain;

import habitrpg.dao.UserDao;

/**
 * A class for getting and creating "user" objects by interfacing with its
 * corresponding DAO (Data access object), UserDao.
 */
public class UserService {

    private UserDao userDao;
    private User loggedIn;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Checks if username exists in database and sets user as logged in.
     *
     * @param username (user input)
     * @return true if login was successful, else if no such username exists in
     * database
     */
    public boolean login(String username) {
        User user = userDao.getOne(username);

        if (user == null) {
            return false;
        }

        this.loggedIn = user;
        return true;
    }

    /**
     * Returns currently logged in user
     *
     * @return logged in user
     */
    public User getLoggedUser() {
        return this.loggedIn;
    }

    /**
     * Adds experience to user based on the difficulty of the completed task
     *
     * @param difficulty (difficulty easy: 1, medium: 2, hard: 3)
     */
    public void addExp(int difficulty) {
        int lvl = this.loggedIn.getLevel();
        int current = this.loggedIn.getExperience();
        switch (difficulty) {
            case 1:
                this.loggedIn.setExperience(current + 25);
                break;
            case 2:
                this.loggedIn.setExperience(current + 50);
                break;
            case 3:
                this.loggedIn.setExperience(current + 100);
                break;
        }

        this.changeLevel(lvl);
    }
    
    /**
     * Calls removeExp(amount) to remove (level * 100) points of experience
     */
    public void experiencePenalty() {
        int lvl = this.loggedIn.getLevel();
        removeExp(lvl * 100);
    }
    
    /**
     * Removes a specified amount of experience from user
     * @param amount (amount to remove)
     */
    public void removeExp(int amount) {
        int lvl = this.loggedIn.getLevel();
        int current = this.loggedIn.getExperience();
        this.loggedIn.setExperience(current - amount);
        changeLevel(lvl);
    }

    /**
     * Changes level of user if experience gained has surpassed the level 
     * limit or gone negative, in which case level is decreased. 
     * @param lvl (level of logged in user)
     */
    private void changeLevel(int lvl) {
        int exp = this.loggedIn.getExperience();
        int nextLevelExp = (lvl * 2) * 1000;

        if (nextLevelExp <= exp) {
            increaseLevel(nextLevelExp, lvl, exp);
        } else if (exp < 0 && 1 < lvl) {
            decreaseLevel(lvl, exp);
        } else if (lvl == 1 && exp < 0) {
            resetLevel();
        }

        userDao.updateUser(this.loggedIn);
    }

    private void resetLevel() {
        this.loggedIn.setLevel(1);
        this.loggedIn.setExperience(0);
        this.loggedIn.setHealth(200);
    }

    private void increaseLevel(int nextLevelExp, int lvl, int exp) {
        this.loggedIn.setLevel(lvl + 1);
        this.loggedIn.setExperience(exp - nextLevelExp);
        this.loggedIn.setHealth((lvl + 1) * 200);
    }

    private void decreaseLevel(int lvl, int exp) {
        this.loggedIn.setLevel(lvl - 1);
        this.loggedIn.setExperience(0);
        this.loggedIn.setHealth((lvl - 1) * 200);
    }

    /**
     * Logs out user.
     *
     */
    public void logout() {
        loggedIn = null;
    }

    /**
     * Creates a "User" object and passes it on to UserDao to be stored in the
     * database.
     *
     * @param username (user input)
     * @param name (user input)
     * @return if creation was successful
     */
    public boolean newUser(String username, String name) {
        if (userDao.getOne(username) != null) {
            return false;
        }
        userDao.create(new User(username, name, 0, 1, 200));
        return true;
    }

}
