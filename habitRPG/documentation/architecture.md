## Structure

The structure of habitRPG adheres to the following structure, and the package structure is as follows:

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/package_diagram.png" width="200">

_habitrpg.ui_ contains a text based ui, _habitrpg.domain_ contains software logic, and _habitrpg.dao_ contains classes for interfacing with the database.

## Software logic

The logical datamodel of the software is made up of four classes: [User](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/User.java), [Todo](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/Todo.java), [Habit](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/Habit.java) and [Daily](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/Daily.java). In addition to these there is a utility class [Time](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/Time.java) for accessing the current date and formatting DataTime-objects. These main relations are representations of users and their various tasks/habits:

<img src="" width="600">

The nature these representations, but since their functionality is different enough it was more clear to also make separate classes responsible for managing these representations. These classes are: [UserService](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/UserService.java), [TodoService](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/TodoService.java), [HabitService](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/HabitService.java) and [DailyService](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/DailyService.java). These classes provide methods for functions of the user interface. Some examples of these methods are:
- boolean login(String username)
- List<Habit> getHabitsUpdate()
- boolean createHabit(String content, int diff)
- boolean untrack(Integer key)
- boolean setDone(int key)

_User/Todo/Habit/DailyService_ only access thier corresponding types of objects. This happens through the classes _User/Todo/Habit/DailyDao_ in the package habitrpg.dao responsible for persistent storage. Instancec of these DAO:s are created in the "Service" classes.

A class/package diagram describing the relations between the various parts of the software:

<img src="" width="450">

### Functionality

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/Login.png" width="850">
