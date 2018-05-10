package habitrpg.ui;

import habitrpg.dao.DailyDao;
import habitrpg.dao.Database;
import habitrpg.dao.DaysShownDao;
import habitrpg.dao.HabitDao;
import habitrpg.dao.TodoDao;
import habitrpg.dao.UserDao;
import habitrpg.domain.Daily;
import habitrpg.domain.DailyService;
import habitrpg.domain.Habit;
import habitrpg.domain.HabitService;
import habitrpg.domain.Time;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
    private DailyService dailyService;
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
    private Label userStatsLabel = new Label();

    private boolean deleteMode;

    @Override
    public void init() throws Exception {

        Database database = new Database();
        database.createDatabase("tracker.db");
        
        UserDao userDao = new UserDao(database);
        HabitDao habitDao = new HabitDao(database);
        TodoDao todoDao = new TodoDao(database);
        DailyDao dailyDao = new DailyDao(database);
        DaysShownDao dsDao = new DaysShownDao(database);
        
        userService = new UserService(userDao);
        habitService = new HabitService(habitDao);
        todoService = new TodoService(todoDao);
        Time time = new Time();
        dailyService = new DailyService(dailyDao, dsDao, time, userService);

    }

    public Node createTodoNode(Todo todo) {
        HBox box = new HBox(10);
        Label label = new Label(todo.getContent());
        label.setMinHeight(28);
        Button button = new Button("done");
        button.setOnAction(e -> {
            todoService.setDone(todo.getId());
            userService.addExp(todo.getDifficulty());
            redrawlist(1);
        });

        Button deleteButton = new Button("del");
        deleteButton.setOnAction(e -> {
            todoService.deleteTodo(todo.getId());
            redrawlist(1);
        });
        
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
        Label label = new Label(habit.getContent());
        label.setMinHeight(28);
        
        Button button = new Button("+");
        button.setOnAction(e -> {
            habitService.addToOrRemoveFromStreak(habit.getId(), 1);
            userService.addExp(habit.getDifficulty());
            redrawlist(2);
        });
        
        Button negativeButton = new Button("-");
        negativeButton.setOnAction(e -> {
            habitService.addToOrRemoveFromStreak(habit.getId(), -1);
            userService.experiencePenalty();
            redrawlist(2);
        });

        Button deleteButton = new Button("del");
        deleteButton.setOnAction(e -> {
            habitService.deleteHabit(habit.getId());
            redrawlist(2);
        });

        Button untrackButton = new Button("untrack");
        untrackButton.setOnAction(e -> {
            habitService.untrack(habit.getId());
            redrawlist(2);
        });
        
        box.setPadding(new Insets(5, 5, 5, 5));
        deleteButton.setStyle("-fx-base: #E74C3C;");

        switch (habit.getDifficulty()) {
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
            box.getChildren().addAll(button, negativeButton, label, untrackButton);
        }

        return box;
    }

    public Node createDailyNode(Daily daily) {
        HBox box = new HBox(10);
        Label label = new Label(daily.getContent());
        label.setMinHeight(28);
        Button button = new Button("done");
        button.setOnAction(e -> {
            dailyService.setDone(daily.getId());
            userService.addExp(daily.getDifficulty());
            redrawlist(3);
        });

        Button deleteButton = new Button("del");
        deleteButton.setOnAction(e -> {
            dailyService.deleteDaily(daily.getId());
            redrawlist(3);
        });

        Button untrackButton = new Button("untrack");
        untrackButton.setOnAction(e -> {
            dailyService.untrack(daily.getId());
            redrawlist(3);
        });

        box.setPadding(new Insets(5, 5, 5, 5));
        deleteButton.setStyle("-fx-base: #E74C3C;");

        switch (daily.getDifficulty()) {
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
            box.getChildren().addAll(button, label, untrackButton);
        }

        return box;
    }

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

            case 3:
                dailyNodes.getChildren().clear();

                List<Daily> undoneDailies = dailyService.getDailiesUpdate();
                undoneDailies.forEach(daily -> {
                    dailyNodes.getChildren().add(createDailyNode(daily));
                });
                break;
        }

        userStatsLabel.setText("PROGRESS: level " 
                    + userService.getLoggedUser().getLevel() 
                    + " | exp " + userService.getLoggedUser().getExperience());
    }

    @Override
    public void start(Stage primaryStage) {

        // login                                                     -------------------------------
        
        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10, 10, 10, 10));
        loginGrid.setVgap(8);
        loginGrid.setHgap(10);

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
            if (userService.login(username)) {
                User user = userService.getLoggedUser();
                todoService.updateUser(user);
                habitService.updateUser(user);
                dailyService.updateUser(user);
                loginMessage.setText("");
                
                menuLabel.setText(username + " : logged in");
                userStatsLabel.setText("PROGRESS: level " + user.getLevel() 
                        + " | exp " + user.getExperience());
                redrawlist(1);
                redrawlist(2);
                redrawlist(3);
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

        Button createNewUserButt = new Button("create");
        newUserGrid.setConstraints(createNewUserButt, 1, 3);

        Button cancelNewUserButt = new Button("go back");
        newUserGrid.setConstraints(cancelNewUserButt, 1, 4);

        Label userCreationMsg = new Label();
        newUserGrid.setConstraints(userCreationMsg, 1, 5);

        createNewUserButt.setOnAction(e -> {
            String username = newUserInput.getText();
            String name = newNameInput.getText();

            if (username.length() == 2 || name.length() < 2) {
                userCreationMsg.setText("username or name too short");
                userCreationMsg.setTextFill(Color.RED);
            } else if (userService.newUser(username, name)) {
                userCreationMsg.setText("");
                loginMessage.setText("new user created");
                loginMessage.setTextFill(Color.GREEN);
                newUserInput.setText("");
                newNameInput.setText("");
                primaryStage.setScene(loginScene);
            } else {
                userCreationMsg.setText("username has to be unique");
                userCreationMsg.setTextFill(Color.RED);
            }
        });

        cancelNewUserButt.setOnAction(e -> {
            newUserInput.setText("");
            newNameInput.setText("");
            primaryStage.setScene(loginScene);
        });

        newUserGrid.getChildren().addAll(userCreationMsg,
                newUserLabel,
                newUserInput,
                newNameLabel,
                createNewUserButt,
                newNameInput,
                cancelNewUserButt);

        newUserGrid.setAlignment(Pos.CENTER);

        newUserScene = new Scene(newUserGrid, 1000, 600);

        
        // create todo                                             --------------------------------
        
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

            if (todo.length() <= 2 || 18 < todo.length()) {
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
        todoScene = new Scene(newTodoGrid, 1000, 600);

        
        // create habit                                            --------------------------------
        
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

            if (habit.length() <= 2 || 18 < habit.length()) {
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

        
        GridPane newDailyGrid = new GridPane();
        newDailyGrid.setPadding(new Insets(10, 10, 10, 10));
        newDailyGrid.setVgap(8);
        newDailyGrid.setHgap(10);

        Label newDailyLabel = new Label("Task");
        newDailyGrid.setConstraints(newDailyLabel, 0, 0);

        TextField newDailyInput = new TextField();
        newDailyInput.setPromptText("read");
        newDailyInput.setFocusTraversable(true);
        newDailyInput.requestFocus();
        newDailyGrid.setConstraints(newDailyInput, 1, 0);

        Label dailyDifficultyLabel = new Label("Effort");
        newDailyGrid.setConstraints(dailyDifficultyLabel, 0, 2);

        //difficulty
        ChoiceBox<String> dailyDifficultyBox = new ChoiceBox<>();
        dailyDifficultyBox.getItems().addAll("Easy", "Medium", "Hard");
        newDailyGrid.setConstraints(dailyDifficultyBox, 1, 2);

        dailyDifficultyBox.setValue("Easy");

        //days of week when shown
        HBox dayBoxes = new HBox(6);

        CheckBox mon = new CheckBox("Mon");
        CheckBox tue = new CheckBox("tue");
        CheckBox wed = new CheckBox("wed");
        CheckBox thu = new CheckBox("thu");
        CheckBox fri = new CheckBox("fri");
        CheckBox sat = new CheckBox("sat");
        CheckBox sun = new CheckBox("sun");
        
        Label daysShown = new Label("Show on");
        newDailyGrid.setConstraints(daysShown, 0, 1);

        dayBoxes.getChildren().addAll(mon, tue, wed, thu, fri, sat, sun);
        newDailyGrid.setConstraints(dayBoxes, 1, 1);

        Button createNewDailyButt = new Button("Create");
        newDailyGrid.setConstraints(createNewDailyButt, 1, 3);

        Button cancelNewDailyButt = new Button("Go back");
        newDailyGrid.setConstraints(cancelNewDailyButt, 1, 4);

        Label dailyCreationMsg = new Label();
        newDailyGrid.setConstraints(dailyCreationMsg, 1, 5);

        createNewDailyButt.setOnAction(e -> {
            String daily = newDailyInput.getText();
            String difficulty = dailyDifficultyBox.getValue();

            boolean[] days = new boolean[8];

            days[1] = mon.isSelected();
            days[2] = tue.isSelected();
            days[3] = wed.isSelected();
            days[4] = thu.isSelected();
            days[5] = fri.isSelected();
            days[6] = sat.isSelected();
            days[7] = sun.isSelected();

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

            if (daily.length() <= 2 || 18 < daily.length()) {
                dailyCreationMsg.setText("description too long or too short");
                dailyCreationMsg.setTextFill(Color.RED);
            } else if (dailyService.createDaily(daily, diff, days)) {
                dailyCreationMsg.setText("");
                newDailyInput.setText("");
                dailyDifficultyBox.setValue("Easy");

                mon.setSelected(false);
                tue.setSelected(false);
                wed.setSelected(false);
                thu.setSelected(false);
                fri.setSelected(false);
                sat.setSelected(false);
                sun.setSelected(false);

                redrawlist(3);
                primaryStage.setScene(trackerScene);
            } else {
                dailyCreationMsg.setText("failed to create task :(");
                dailyCreationMsg.setTextFill(Color.RED);
            }
        });

        cancelNewDailyButt.setOnAction(e -> {
            newDailyInput.setText("");
            dailyDifficultyBox.setValue("Easy");
            mon.setSelected(false);
            tue.setSelected(false);
            wed.setSelected(false);
            thu.setSelected(false);
            fri.setSelected(false);
            sat.setSelected(false);
            sun.setSelected(false);
            primaryStage.setScene(trackerScene);
        });

        newDailyGrid.getChildren().addAll(newDailyLabel,
                newDailyInput,
                dailyDifficultyLabel,
                dailyDifficultyBox,
                createNewDailyButt,
                cancelNewDailyButt,
                dailyCreationMsg,
                dayBoxes,
                daysShown);

        newDailyGrid.setAlignment(Pos.CENTER);
        dailyScene = new Scene(newDailyGrid, 1000, 600);

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
        
        createNewDaily.setOnAction(e -> {
            primaryStage.setScene(dailyScene);
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
        trackerPane.getChildren().addAll(habits, dailies, todos);

        trackerScene = new Scene(verticalLayout, 1000, 600);

        menuLabel.setStyle("-fx-font-size: 20px;");
        userStatsLabel.setStyle("-fx-font-size: 20px;");
        HBox menuPane = new HBox(15);
        menuPane.setPadding(new Insets(10, 10, 10, 10));

        Button logoutButton = new Button("logout");
        Button deleteModeButton = new Button("delete mode");

        menuPane.getChildren().addAll(menuLabel, 
                logoutButton, 
                deleteModeButton, 
                userStatsLabel);

        deleteModeButton.setOnAction(e -> {

            if (deleteMode) {
                deleteModeButton.setStyle("-fx-base: #f4f4f4;");
            } else {
                deleteModeButton.setStyle("-fx-base: #E74C3C;");
            }

            deleteMode = !deleteMode;
            redrawlist(1);
            redrawlist(2);
            redrawlist(3);
        });

        logoutButton.setOnAction(e -> {
            userService.logout();
            todoService.updateUser(null);
            habitService.updateUser(null);
            dailyService.updateUser(null);
            deleteMode = false;
            if (deleteMode) {
                deleteModeButton.setStyle("-fx-base: #E74C3C;");
            } else {
                deleteModeButton.setStyle("-fx-base: #f4f4f4;");
            }
            
            primaryStage.setScene(loginScene);
        });

        todoNodes = new VBox(0);
        todoNodes.setMaxWidth(310);
        todoNodes.setMinWidth(310);

        habitNodes = new VBox(0);
        habitNodes.setMaxWidth(310);
        habitNodes.setMinWidth(310);

        dailyNodes = new VBox(0);
        dailyNodes.setMaxWidth(310);
        dailyNodes.setMinWidth(310);

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
