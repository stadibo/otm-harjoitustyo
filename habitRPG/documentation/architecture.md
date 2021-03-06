## Structure

The structure of habitRPG adheres to the following structure, and the package structure is as follows:

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/package_diagram.png" width="200">

_habitrpg.ui_ contains a text based ui, _habitrpg.domain_ contains software logic, and _habitrpg.dao_ contains classes for interfacing with the database.

## User interface

The UI contains 6 different scenes
- login
- create new user
- main tracker view with to-do:s, habits and daily tasks
- create new to-do
- create new habit
- create new daily task

each scene is implemented as a [Scene](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html)-object. Only one scene is shown at one time, contained in the applications' [stage](https://docs.oracle.com/javase/8/javafx/api/javafx/stage/Stage.html). The user interface is built software wise in the class [habitrpg.ui.TrackerUi](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/ui/TrackerUi.java).

The UI has mostly been isolated from the software logic and only calls for "Service"-class methods in [habitrpg.domain](https://github.com/stadibo/otm-harjoitustyo/tree/master/habitRPG/src/main/java/habitrpg/domain) when needed with appropriate parameters.

When the state of a to-do/habit/daily list changes, e.g. a user logs in or a task is set done/deleted/created, the method [redrawList()](https://github.com/stadibo/otm-harjoitustyo/blob/096412cb38dc08a734d7d8017583574d3cf5da9e/habitRPG/src/main/java/habitrpg/ui/TrackerUi.java#L247) is called with corresponding parameter (1:to-do, 2:habit, 3:daily) which rerenders lists on the trackerScene with updated objects obtained from software logic.

## Software logic

The logical datamodel of the software is made up of four classes: [User](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/User.java), [Todo](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/Todo.java), [Habit](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/Habit.java) and [Daily](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/Daily.java). In addition to these there is a utility class [Time](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/Time.java) for accessing the current date and formatting DataTime-objects using the [JodaTime](http://www.joda.org/joda-time/) external library. These main relations are representations of users and their various tasks/habits:

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/Logical_datamodel2.png" width="500">

The nature these representations, but since their functionality is different enough it was more clear to also make separate classes responsible for managing these representations. These classes are: [UserService](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/UserService.java), [TodoService](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/TodoService.java), [HabitService](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/HabitService.java) and [DailyService](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/DailyService.java). These classes provide methods for functions of the user interface. Some examples of these methods are:
- boolean login(String username)
- List<Habit> getHabitsUpdate()
- boolean createHabit(String content, int diff)
- boolean untrack(Integer key)
- boolean setDone(int key)

_User/Todo/Habit/DailyService_ only access thier corresponding types of objects. This happens through the classes _User/Todo/Habit/DailyDao_ in the package habitrpg.dao responsible for persistent storage. Instances of these DAO:s are created in the "Service" classes.

A class/package diagram describing the relations between the various parts of the software:

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/package_diagram2.png" width="700">

## Data persistence

The "DatabaseDao" classes in [habitrpg.dao](https://github.com/stadibo/otm-harjoitustyo/tree/master/habitRPG/src/main/java/habitrpg/dao) are responsible for saving data into a database file called _tracker.db_. The file will appear either in the same folder as the jar, at least on OSX, or in the home folder in a Linux environment.

These classes follow the [Data Access Object](https://en.wikipedia.org/wiki/Data_access_object) design pattern and can be replaced with another implementation if needed. The "DatabaseDao"-classes have abstractions and are not used directly by the software logic.

### Database schema

Each DAO represents a table in the database. The schema or [Create table statements](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/documentation/misc/Create_Table_Statements) for the database.

### Main functionality

For basically all functions there is a reactionary eventhandler that calls a specific software logic _appService_ method, after which the _appService_ calls the _appDao_ to get data from storage or to store data.

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/Login.png" width="850">

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/Create%20new%20user.png" width="850">

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/Create%20new%20task.png" width="850">

Create new habit/todo follows the same principle as create new daily task, but without the days to be shown on.

## Structural weaknesses

### UI

The GUI has be chopped up into methods for updating elements and creating scenes, etc. but is still quite difficult to maintain. An improvement could be to migrate to the FXML-format for creating structure to the UI. Then the logic components of the UI would not be lost in element definitions.

### DAO

DatabaseDao-classes could be simplified to inject the statement and only query in the DatabaseDao.
