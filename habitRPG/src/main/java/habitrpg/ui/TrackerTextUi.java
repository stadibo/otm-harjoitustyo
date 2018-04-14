/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.ui;

import habitrpg.dao.Database;
import habitrpg.domain.UserService;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author peje
 */
public class TrackerTextUi {

    private UserService userService;
    private Database database;
    private Scanner scanner;
    private Map<String, String> commands;

    public TrackerTextUi(Scanner scanner, Database db) throws ClassNotFoundException, SQLException {

        this.database = db;
        userService = new UserService(db);
        this.scanner = scanner;

        commands = new TreeMap<>();

        commands.put("1", "1 show habits //not implemented yet");
        commands.put("2", "2 show to-do:s //not implemented yet");
        commands.put("3", "3 show dailies //not implemented yet");
        commands.put("e", "e logout");
        commands.put("h", "h help");
        commands.put("x", "x quit");
        commands.put("l", "l login");
        commands.put("c", "c create user");
    }

    public void run() {
        System.out.println("HABIT TRACKER");

        while (true) {
            if (userService.getLoggedUser() == null) {
                if (!login()) {
                    break;
                }
            }
            printCommands();

            System.out.println();
            System.out.print("> ");
            String command = scanner.nextLine();
            System.out.println();
            if (!commands.keySet().contains(command)) {
                System.out.println("false command.");
                printCommands();
            }

            if (command.equals("x")) {
                break;
            } else if (command.equals("1")) {
                showHabits();
            } else if (command.equals("2")) {
                showTodos();
            } else if (command.equals("3")) {
                showDailies();
            } else if (command.equals("e")) {
                logout();
            } else if (command.equals("h")) {
                printCommands();
            }
        }
    }

    private boolean login() {

        System.out.println();
        loginCommands();

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();
            System.out.println();

            if (!commands.keySet().contains(command)) {
                System.out.println("false command.");
                loginCommands();
            }

            if (command.equals("l")) {
                if (attemptLogin()) {
                    return true;
                }
            } else if (command.equals("c")) {
                createUser();
            } else if (command.equals("x")) {
                return false;
            }
        }

    }

    private void createUser() {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Motto: ");
        String motto = scanner.nextLine();

        try {
            if (userService.newUser(username, name, motto)) {
                System.out.println("New user created");
            } else {
                System.out.println("Username taken");
            }
        } catch (SQLException e) {
            System.out.println("Could not create user");
        }
    }

    private boolean attemptLogin() {
        try {
            while (true) {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                boolean success = userService.login(username);
                if (!success) {
                    System.out.println("username not found");
                    return false;
                } else {
                    System.out.println();
                    System.out.println('"' + userService.getLoggedUser().getMotto() + '"');
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Could not obtain user");
            return false;
        }

    }

    private void logout() {
        userService.logout();
    }

    private void showHabits() {

    }

    private void showTodos() {

    }

    private void showDailies() {

    }

    private void loginCommands() {
        System.out.println("Available commands:");
        System.out.println(commands.get("l"));
        System.out.println(commands.get("c"));
        System.out.println(commands.get("x"));
    }

    private void printCommands() {
        System.out.println("Available commands:");
        commands.entrySet().forEach(k -> {
            if (k.getKey().equals("l") || k.getKey().equals("c")) {

            } else {
                System.out.println(k.getValue());
            }
        });
    }

}
