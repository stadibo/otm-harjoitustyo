/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*package habitrpg.ui;

import habitrpg.dao.Database;
import habitrpg.domain.Habit;
import habitrpg.domain.HabitService;
import habitrpg.domain.Todo;
import habitrpg.domain.TodoService;
import habitrpg.domain.UserService;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author peje
 */
/*

public class TrackerTextUi {

    private UserService userService;
    private TodoService todoService;
    private HabitService habitService;
    private Database database;
    private Scanner scanner;
    private Map<String, String> commands;

    public TrackerTextUi(Scanner scanner, Database db) throws ClassNotFoundException, SQLException {

        this.database = db;
        userService = new UserService(db);

        this.scanner = scanner;

        commands = new TreeMap<>();

        commands.put("1", "1 habit view");
        commands.put("2", "2 to-do view");
        commands.put("3", "3 daily view //not implemented yet");
        commands.put("r", "r retire");
        commands.put("m", "m mark done");
        commands.put("b", "b go back");
        commands.put("a", "a add to streak");
        commands.put("d", "d delete");
        commands.put("e", "e logout");
        commands.put("h", "h help");
        commands.put("x", "x quit");
        commands.put("l", "l login");
        commands.put("c", "c create");
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
            System.out.print("Command: ");
            String command = scanner.nextLine();
            if (!commands.keySet().contains(command)) {
                System.out.println("false command.");
            }

            if (command.equals("x")) {
                break;
            } else if (command.equals("1")) {
                habitView();
            } else if (command.equals("2")) {
                todoView();
            } else if (command.equals("3")) {
                //dailyView();
            } else if (command.equals("e")) {
                logout();
            } else if (command.equals("h")) {
                printCommands();
            }
            System.out.println();

        }
    }

    private boolean login() {

        System.out.println();
        loginCommands();

        while (true) {
            System.out.print("Command: ");
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

        if (userService.newUser(username, name, motto)) {
            System.out.println("New user created");
        } else {
            System.out.println("Username taken");
        }
    }

    private boolean attemptLogin() {
        while (true) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            boolean success = userService.login(username);
            if (!success) {
                System.out.println("username not found");
                return false;
            } else {
                todoService = new TodoService(database, userService.getLoggedUser());
                habitService = new HabitService(database, userService.getLoggedUser());
                System.out.println();
                System.out.println('"' + userService.getLoggedUser().getMotto() + '"');
                return true;
            }
        }
    }

    private void logout() {
        userService.logout();
    }

    private void habitView() {
        System.out.println("HABIT VIEW");
        while (true) {
            System.out.println();
            habitCommands();
            System.out.println();
            showHabits();

            System.out.print("Command: ");
            String command = scanner.nextLine();
            System.out.println();

            if (!commands.keySet().contains(command)) {
                System.out.println("false command.");
                //habitCommands();
            }

            if (command.equals("r")) {
                retireHabit();
            } else if (command.equals("a")) {
                addToStreak();
            } else if (command.equals("d")) {
                deleteHabit();
            } else if (command.equals("c")) {
                createHabit();
            } else if (command.equals("b")) {
                break;
            }
        }
    }

    private void todoView() {
        System.out.println("TO-DO VIEW");
        while (true) {
            System.out.println();
            todoCommands();
            System.out.println();
            showTodos();

            System.out.print("Command: ");
            String command = scanner.nextLine();
            System.out.println();

            if (!commands.keySet().contains(command)) {
                System.out.println("false command.");
                //todoCommands();
            }

            if (command.equals("m")) {
                setDone(1);
            } else if (command.equals("d")) {
                deleteTodo();
            } else if (command.equals("c")) {
                createTodo();
            } else if (command.equals("b")) {
                break;
            }
        }
    }

    private void showHabits() {
        System.out.println("HABITS:");
        List<Habit> habits = habitService.getHabitsUpdate();
        if (habits.isEmpty()) {
            System.out.println("No habits to show.");
        } else {
            for (int i = 0; i < habits.size(); i++) {
                System.out.println("id:" + (i + 1) + "    " + habits.get(i).toString());
            }
        }
    }

    private void showTodos() {
        System.out.println("TO-DO:");
        List<Todo> todos = todoService.getTodosUpdate();
        if (todos.isEmpty()) {
            System.out.println("No to-do:s to show.");
        } else {
            for (int i = 0; i < todos.size(); i++) {
                System.out.println("id:" + (i + 1) + "    " + todos.get(i).toString());
            }
        }
    }

    private void showDailies() {

    }

    private void todoCommands() {
        System.out.println("Available commands:");
        System.out.println(commands.get("d"));
        System.out.println(commands.get("m"));
        System.out.println(commands.get("c"));
        System.out.println(commands.get("b"));
    }

    private void habitCommands() {
        System.out.println("Available commands:");
        System.out.println(commands.get("d"));
        System.out.println(commands.get("r"));
        System.out.println(commands.get("c"));
        System.out.println(commands.get("a"));
        System.out.println(commands.get("b"));
    }

    private void dailyCommands() {
        System.out.println("Available commands:");
        System.out.println(commands.get("d"));
        System.out.println(commands.get("r"));
        System.out.println(commands.get("c"));
        System.out.println(commands.get("m"));
        System.out.println(commands.get("b"));
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
            if (k.getKey().equals("l") || k.getKey().equals("c")
                    || k.getKey().equals("r") || k.getKey().equals("d")
                    || k.getKey().equals("a") || k.getKey().equals("m")
                    || k.getKey().equals("b")) {

            } else {
                System.out.println(k.getValue());
            }
        });
    }

    private void createTodo() {
        System.out.print("To-do: ");
        String content = scanner.nextLine();

        System.out.println("1 = trivial, 2 = medium, 3 = hard");
        System.out.print("Difficulty: ");
        int difficulty = 0;

        try {
            difficulty = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Please, enter digit matching a difficulty.");
        }

        if (difficulty == 1 || difficulty == 2 || difficulty == 3) {
            todoService.createTodo(content, difficulty);
        } else {
            System.out.println("Could not create to-do. Try again.");
        }
    }

    private void createHabit() {
        System.out.print("Habit: ");
        String content = scanner.nextLine();

        System.out.println("1 = trivial, 2 = medium, 3 = hard");
        System.out.print("Difficulty: ");
        int difficulty = 0;

        try {
            difficulty = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Please, enter digit matching a difficulty.");
        }

        if (difficulty == 1 || difficulty == 2 || difficulty == 3) {
            habitService.createHabit(content, difficulty);
        } else {
            System.out.println("Could not create habit. Try again.");
        }
    }

    private void createDaily() {

    }

    private void retireHabit() {
        System.out.print("Enter id: ");
        int id = -1;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Please, enter an integer.");
        }

        if (id != -1) {
            if (!habitService.setDone(id)) {
                System.out.println("Id doesn't exist.");
            }

        } else {
            System.out.println("Could not retire habit. Try again.");
        }
    }

    private void addToStreak() {
        System.out.print("Enter id: ");
        int id = -1;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Please, enter an integer.");
        }

        if (id != -1) {
            if (!habitService.addToStreak(id)) {
                System.out.println("Id doesn't exist.");
            }

        } else {
            System.out.println("Could not add to streak. Try again.");
        }
    }

    private void setDone(int type) {
        System.out.print("Enter id: ");
        int id = -1;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Please, enter an integer.");
        }

        if (id != -1) {
            if (!todoService.setDone(id)) {
                System.out.println("Id doesn't exist.");
            }

        } else {
            System.out.println("Could not mark done. Try again.");
        }
    }

    private void deleteHabit() {
        System.out.print("Enter id: ");
        int id = -1;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Please, enter an integer.");
        }

        if (id != -1) {
            if (!habitService.deleteHabit(id)) {
                System.out.println("Id doesn't exist.");
            }
        } else {
            System.out.println("Could not delete habit. Try again.");
        }
    }

    private void deleteTodo() {
        System.out.print("Enter id: ");
        int id = -1;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Please, enter an integer.");
        }

        if (id != -1) {
            if (!todoService.deleteTodo(id)) {
                System.out.println("Id doesn't exist.");
            }

        } else {
            System.out.println("Could not delete todo. Try again.");
        }
    }

    private void dailyView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

*/