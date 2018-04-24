/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitrpg.ui;

import habitrpg.dao.Database;
import habitrpg.domain.Habit;
import habitrpg.domain.HabitService;
import habitrpg.domain.Todo;
import habitrpg.domain.TodoService;
import habitrpg.domain.User;
import habitrpg.domain.UserService;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author peje
 */
public class TrackerUi extends Application {

    private UserService userService;
    private TodoService todoService;
    private HabitService habitService;
    private Database database;
    private Scene todoScene;
    private Scene habitScene;
    private Scene dailyScene;
    private Scene trackerScene;
    private Scene newUserScene;
    private Scene loginScene;

    private VBox todoNodes;
    private VBox habitNodes;
    private VBox dailyNodes;
    private Label menuLabel = new Label();

    private boolean deleteMode;

    @Override
    public void init() throws Exception {

        database = new Database();
        database.createDatabase("tracker.db");

        userService = new UserService(database);
        habitService = new HabitService(database);
        todoService = new TodoService(database);

    }

    public Node createTodoNode(Todo todo) {
        HBox box = new HBox(10);
        Label label = new Label(todo.getContent());
        label.setMinHeight(28);
        Button button = new Button("done");
        button.setOnAction(e -> {
            todoService.setDoneGui(todo.getId());
            redrawlist(1);
        });

        Button deleteButton = new Button("del");
        deleteButton.setOnAction(e -> {
            todoService.deleteTodoGui(todo.getId());
            redrawlist(1);
        });
        //Region spacer = new Region();
        //HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(5, 5, 5, 5));
        deleteButton.setStyle("-fx-base: #E74C3C;");

        switch (todo.getDifficulty()) {
            case 1:
                box.setStyle("-fx-background-color: #ABEBC6; -fx-base: #58D68D;");
                break;
            case 2:
                box.setStyle("-fx-background-color: #AED6F1; -fx-base: #5DADE2;");
                break;
            case 3:
                box.setStyle("-fx-background-color: #D7BDE2; -fx-base: #AF7AC5;");
                break;
        }

        if (deleteMode) {
            box.getChildren().addAll(deleteButton, label);
        } else {
            box.getChildren().addAll(button, label);
        }

        return box;
    }

    public Node createHabitNode(Habit habit) {
        HBox box = new HBox(10);
        Label label = new Label(habit.getContent() + " // streak: " + habit.getCurrentStreak());
        label.setMinHeight(28);
        Button button = new Button("+");
        button.setOnAction(e -> {
            habitService.addToStreakGui(habit.getId());
            redrawlist(2);
        });

        Button deleteButton = new Button("del");
        deleteButton.setOnAction(e -> {
            habitService.deleteHabitGui(habit.getId());
            redrawlist(2);
        });

        Button untrackButton = new Button("untrack");
        untrackButton.setOnAction(e -> {
            habitService.untrack(habit.getId());
            redrawlist(2);
        });

//        Region spacer = new Region();
//        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(5, 5, 5, 5));
        deleteButton.setStyle("-fx-base: #E74C3C;");

        box.setStyle("-fx-background-color: #E5E8E8; -fx-base: #E5E8E8;");

        if (deleteMode) {
            box.getChildren().addAll(deleteButton, label);
        } else {
            box.getChildren().addAll(button, label, untrackButton);
        }

        return box;
    }

//    public Node createDailyNode(Daily daily) {
//        HBox box = new HBox(10);
//        Label label = new Label(daily.getContent());
//        label.setMinHeight(28);
//        Button button = new Button("done");
//        button.setOnAction(e -> {
//            habitService.untrack(daily.getId());
//            redrawlist(3);
//        });
//
//        Button deleteButton = new Button("del");
//
//        box.setPadding(new Insets(0, 5, 0, 5));
//
//        box.getChildren().addAll(button, label, deleteButton);
//        return box;
//    }

    public void redrawlist(int type) {
        switch (type) {
            case 1:
                todoNodes.getChildren().clear();

                List<Todo> undoneTodos = todoService.getTodosUpdate();
                undoneTodos.forEach(todo -> {
                    todoNodes.getChildren().add(createTodoNode(todo));
                });
                break;
            case 2:
                habitNodes.getChildren().clear();

                List<Habit> undoneHabits = habitService.getHabitsUpdate();
                undoneHabits.forEach(habit -> {
                    habitNodes.getChildren().add(createHabitNode(habit));
                });
                break;
            //case 3:
        }

    }

    @Override
    public void start(Stage primaryStage) {

        // login                                                     -------------------------------
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10, 10, 10, 10));
        loginGrid.setVgap(8);
        loginGrid.setHgap(10);

        menuLabel.setStyle("-fx-font-size: 20px;");

        Label nameLabel = new Label("Username");
        loginGrid.setConstraints(nameLabel, 0, 0);

        TextField nameInput = new TextField();
        nameInput.setPromptText("tester name");
        loginGrid.setConstraints(nameInput, 1, 0);

        Button loginButton = new Button("Login");
        Button createButton = new Button("Create new user");
        loginGrid.setConstraints(loginButton, 1, 1);
        loginGrid.setConstraints(createButton, 1, 2);

        Label loginMessage = new Label();
        loginGrid.setConstraints(loginMessage, 1, 3);

        loginButton.setOnAction(e -> {
            String username = nameInput.getText();
            menuLabel.setText(username + " : logged in");
            if (userService.login(username)) {
                User user = userService.getLoggedUser();
                todoService.updateUser(user);
                habitService.updateUser(user);
                loginMessage.setText("");
                redrawlist(1);
                redrawlist(2);
                primaryStage.setScene(trackerScene);
                nameInput.setText("");
            } else {
                loginMessage.setText("user doesn't exist");
                loginMessage.setTextFill(Color.RED);
            }
        });

        createButton.setOnAction(e -> {
            nameInput.setText("");
            primaryStage.setScene(newUserScene);
        });

        loginGrid.getChildren().addAll(loginMessage,
                nameInput,
                nameLabel,
                loginButton,
                createButton);

        loginGrid.setAlignment(Pos.CENTER);

        loginScene = new Scene(loginGrid, 1000, 600);

        // new user                                                -------------------------------
        GridPane newUserGrid = new GridPane();
        newUserGrid.setPadding(new Insets(10, 10, 10, 10));
        newUserGrid.setVgap(8);
        newUserGrid.setHgap(10);

        Label newUserLabel = new Label("Username");
        newUserGrid.setConstraints(newUserLabel, 0, 0);

        TextField newUserInput = new TextField();
        newUserInput.setPromptText("testerUser");
        newUserGrid.setConstraints(newUserInput, 1, 0);

        Label newNameLabel = new Label("Name");
        newUserGrid.setConstraints(newNameLabel, 0, 1);

        TextField newNameInput = new TextField();
        newNameInput.setPromptText("Ellon Mushk");
        newUserGrid.setConstraints(newNameInput, 1, 1);

        Label newMottoLabel = new Label("Quote");
        newUserGrid.setConstraints(newMottoLabel, 0, 2);

        TextField newMottoInput = new TextField();
        newMottoInput.setPromptText("\"A product that needs a manual is broken.\"");
        newUserGrid.setConstraints(newMottoInput, 1, 2);

        Button createNewUserButt = new Button("create");
        newUserGrid.setConstraints(createNewUserButt, 1, 4);

        Button cancelNewUserButt = new Button("go back");
        newUserGrid.setConstraints(cancelNewUserButt, 1, 5);

        Label userCreationMsg = new Label();
        newUserGrid.setConstraints(userCreationMsg, 1, 6);

        createNewUserButt.setOnAction(e -> {
            String username = newUserInput.getText();
            String name = newNameInput.getText();
            String motto = newMottoInput.getText();

            if (username.length() == 2 || name.length() < 2) {
                userCreationMsg.setText("username or name too short");
                userCreationMsg.setTextFill(Color.RED);
            } else if (userService.newUser(username, name, motto)) {
                userCreationMsg.setText("");
                loginMessage.setText("new user created");
                loginMessage.setTextFill(Color.GREEN);
                newUserInput.setText("");
                newNameInput.setText("");
                newMottoInput.setText("");
                primaryStage.setScene(loginScene);
            } else {
                userCreationMsg.setText("username has to be unique");
                userCreationMsg.setTextFill(Color.RED);
            }
        });

        cancelNewUserButt.setOnAction(e -> {
            newUserInput.setText("");
            newNameInput.setText("");
            newMottoInput.setText("");
            primaryStage.setScene(loginScene);
        });

        newUserGrid.getChildren().addAll(userCreationMsg,
                newUserLabel,
                newUserInput,
                newNameLabel,
                createNewUserButt,
                newNameInput,
                newMottoLabel,
                newMottoInput,
                cancelNewUserButt);

        newUserGrid.setAlignment(Pos.CENTER);

        newUserScene = new Scene(newUserGrid, 1000, 600);

        // create todo                                             --------------------------------
        BorderPane bp1 = new BorderPane();

        GridPane newTodoGrid = new GridPane();
        newTodoGrid.setPadding(new Insets(10, 10, 10, 10));
        newTodoGrid.setVgap(8);
        newTodoGrid.setHgap(10);

        Label newTodoLabel = new Label("To-do");
        newTodoGrid.setConstraints(newTodoLabel, 0, 0);

        TextField newTodoInput = new TextField();
        newTodoInput.setPromptText("meditate");
        newTodoGrid.setConstraints(newTodoInput, 1, 0);

        Label todoDifficultyLabel = new Label("Effort");
        newTodoGrid.setConstraints(todoDifficultyLabel, 0, 1);

        ChoiceBox<String> difficultyBox = new ChoiceBox<>();
        difficultyBox.getItems().addAll("Easy", "Medium", "Hard");
        newTodoGrid.setConstraints(difficultyBox, 1, 1);

        difficultyBox.setValue("Easy");

        Button createNewTodoButt = new Button("Create");
        newTodoGrid.setConstraints(createNewTodoButt, 1, 2);

        Button cancelNewTodoButt = new Button("Go back");
        newTodoGrid.setConstraints(cancelNewTodoButt, 1, 3);

        Label todoCreationMsg = new Label();
        newTodoGrid.setConstraints(todoCreationMsg, 1, 4);

        createNewTodoButt.setOnAction(e -> {
            String todo = newTodoInput.getText();
            String difficulty = difficultyBox.getValue();

            int diff = 1;
            switch (difficulty) {
                case "Easy":
                    diff = 1;
                    break;
                case "Medium":
                    diff = 2;
                    break;
                case "Hard":
                    diff = 3;
                    break;
            }

            if (todo.length() <= 2 || 30 < todo.length()) {
                todoCreationMsg.setText("description too long or too short");
                todoCreationMsg.setTextFill(Color.RED);
            } else if (todoService.createTodo(todo, diff)) {
                todoCreationMsg.setText("");
                newTodoInput.setText("");
                difficultyBox.setValue("Easy");
                redrawlist(1);
                primaryStage.setScene(trackerScene);
            } else {
                todoCreationMsg.setText("failed to create todo :(");
                todoCreationMsg.setTextFill(Color.RED);
            }
        });

        cancelNewTodoButt.setOnAction(e -> {
            newTodoInput.setText("");
            difficultyBox.setValue("Easy");
            primaryStage.setScene(trackerScene);
        });

        newTodoGrid.getChildren().addAll(newTodoLabel,
                newTodoInput,
                todoDifficultyLabel,
                createNewTodoButt,
                difficultyBox,
                cancelNewTodoButt,
                todoCreationMsg);

        newTodoGrid.setAlignment(Pos.CENTER);
        //bp1.getChildren().addAll(newTodoGrid);

        //BorderPane.setAlignment(newTodoGrid, Pos.CENTER);
        todoScene = new Scene(newTodoGrid, 1000, 600);

        // create habit                                            --------------------------------
        BorderPane bp2 = new BorderPane();

        GridPane newHabitGrid = new GridPane();
        newHabitGrid.setPadding(new Insets(10, 10, 10, 10));
        newHabitGrid.setVgap(8);
        newHabitGrid.setHgap(10);

        Label newHabitLabel = new Label("Habit");
        newHabitGrid.setConstraints(newHabitLabel, 0, 0);

        TextField newHabitInput = new TextField();
        newHabitInput.setPromptText("read");
        newHabitInput.setFocusTraversable(true);
        newHabitInput.requestFocus();
        newHabitGrid.setConstraints(newHabitInput, 1, 0);

        Label habitDifficultyLabel = new Label("Effort");
        newHabitGrid.setConstraints(habitDifficultyLabel, 0, 1);

        ChoiceBox<String> habitDifficultyBox = new ChoiceBox<>();
        habitDifficultyBox.getItems().addAll("Easy", "Medium", "Hard");
        newHabitGrid.setConstraints(habitDifficultyBox, 1, 1);

        habitDifficultyBox.setValue("Easy");

        Button createNewHabitButt = new Button("Create");
        newHabitGrid.setConstraints(createNewHabitButt, 1, 2);

        Button cancelNewHabitButt = new Button("Go back");
        newHabitGrid.setConstraints(cancelNewHabitButt, 1, 3);

        Label habitCreationMsg = new Label();
        newHabitGrid.setConstraints(habitCreationMsg, 1, 4);

        createNewHabitButt.setOnAction(e -> {
            String habit = newHabitInput.getText();
            String difficulty = habitDifficultyBox.getValue();

            int diff = 1;
            switch (difficulty) {
                case "Easy":
                    diff = 1;
                    break;
                case "Medium":
                    diff = 2;
                    break;
                case "Hard":
                    diff = 3;
                    break;
            }

            if (habit.length() <= 2 || 24 < habit.length()) {
                habitCreationMsg.setText("description too long or too short");
                habitCreationMsg.setTextFill(Color.RED);
            } else if (habitService.createHabit(habit, diff)) {
                habitCreationMsg.setText("");
                newHabitInput.setText("");
                habitDifficultyBox.setValue("Easy");
                redrawlist(2);
                primaryStage.setScene(trackerScene);
            } else {
                habitCreationMsg.setText("failed to create habit :(");
                habitCreationMsg.setTextFill(Color.RED);
            }
        });

        cancelNewHabitButt.setOnAction(e -> {
            newHabitInput.setText("");
            habitDifficultyBox.setValue("Easy");
            primaryStage.setScene(trackerScene);
        });

        newHabitGrid.getChildren().addAll(newHabitLabel,
                newHabitInput,
                habitDifficultyLabel,
                habitDifficultyBox,
                createNewHabitButt,
                cancelNewHabitButt,
                habitCreationMsg);

        newHabitGrid.setAlignment(Pos.CENTER);
        habitScene = new Scene(newHabitGrid, 1000, 600);

        // create daily                                            --------------------------------
        // tracker scene                                            --------------------------------
        Button createNewTodo = new Button("New to-do");
        Button createNewHabit = new Button("New habit");
        Button createNewDaily = new Button("New daily task");

        createNewTodo.setOnAction(e -> {
            primaryStage.setScene(todoScene);
        });

        createNewHabit.setOnAction(e -> {
            primaryStage.setScene(habitScene);
        });

        VBox verticalLayout = new VBox(10);
        HBox trackerPane = new HBox(10);

        VBox habits = new VBox(15);
        habits.setMinWidth(250);

        VBox dailies = new VBox(15);
        dailies.setMinWidth(250);

        VBox todos = new VBox(15);
        todos.setMinWidth(250);

        ScrollPane todoScrollbar = new ScrollPane();
        ScrollPane habitScrollbar = new ScrollPane();
        ScrollPane dailyScrollbar = new ScrollPane();

        habits.getChildren().addAll(createNewHabit, habitScrollbar);
        dailies.getChildren().addAll(createNewDaily, dailyScrollbar);
        todos.getChildren().addAll(createNewTodo, todoScrollbar);

        trackerPane.setPadding(new Insets(10, 10, 10, 10));
        //verticalLayout.setStyle("-fx-background-color: DAE6F3;");
        trackerPane.getChildren().addAll(habits, dailies, todos);

        trackerScene = new Scene(verticalLayout, 1000, 600);

        HBox menuPane = new HBox(15);
        menuPane.setPadding(new Insets(10, 10, 10, 10));

        Button logoutButton = new Button("logout");
        Button deleteModeButton = new Button("delete mode");

        menuPane.getChildren().addAll(menuLabel, logoutButton, deleteModeButton);

        deleteModeButton.setOnAction(e -> {

            if (deleteMode) {
                deleteModeButton.setStyle("-fx-base: #d0d0d0;");
            } else {
                deleteModeButton.setStyle("-fx-base: #E74C3C;");
            }

            deleteMode = !deleteMode;
            redrawlist(1);
            redrawlist(2);
        });

        logoutButton.setOnAction(e -> {
            userService.logout();
            todoService.updateUser(null);
            habitService.updateUser(null);
            deleteMode = !deleteMode;
            primaryStage.setScene(loginScene);
        });

        todoNodes = new VBox(0);
        todoNodes.setMaxWidth(300);
        todoNodes.setMinWidth(300);
        //redrawlist();

        habitNodes = new VBox(0);
        habitNodes.setMaxWidth(300);
        habitNodes.setMinWidth(300);

        dailyNodes = new VBox(0);
        dailyNodes.setMaxWidth(300);
        dailyNodes.setMinWidth(300);

        verticalLayout.getChildren().addAll(menuPane, trackerPane);

        todoScrollbar.setContent(todoNodes);
        habitScrollbar.setContent(habitNodes);
        dailyScrollbar.setContent(dailyNodes);

        todoScrollbar.setMinWidth(320);
        habitScrollbar.setMinWidth(320);
        dailyScrollbar.setMinWidth(320);

        // setup primary stage
        primaryStage.setTitle("Habit Tracker");
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
