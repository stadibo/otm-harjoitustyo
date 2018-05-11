# User guide

Download the file [habitRPG.jar](https://github.com/stadibo/otm-harjoitustyo/releases/tag/v1.2)

## Configuration

SQLite3 might need to be installed for the database to work.

## Running the application

The application is run by typing the command 

```
java -jar habitRPG.jar
```
or on some systems by double clicking jar file in file explorer.

## Login

The application opens into the login view:

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/gui_login.png" width="500">

Login is done by typing an existing username into the text field and pressing _Login_.

## Create new user

From the login view it is possible to move to creating a new user in another view by pressing _Create new user_.

A new user is created by filling the fields with the required info and pressing _Create_

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/createUserNew.png" width="500">

If the creation is successful the application transitions back to the login view.

## Creating "tasks" and using actions associated to them

After a successful login the application transitions to the main view where the user can see all their tracked habits and daily task, as well as their undone to-do:s

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/userProgress.png" width="500">

The view enables the user to mark to-do:s as done with the button _done_. Daily tasks can also be marked done but will be marked undone the next day it is set to appear on. A habit streak can be increased by pressing _+_. For this user will gain experience. Likewise habit streak can be decreased by pressing _-_. User will recieve  penalty, decrease experience, for not succeeding on a habit. It is up to the user to be honest and mark failures. To no longer show a "daily" or habit press the button _untrack_.

Above the lists are _new..._ buttons. Pressing these will open the create "task" view. Fill in the text field and choose effort for completing task, and for daily tasks the user can choose on which days the task will be shown by ticking boxes.

pressing _Create_ will create new task and bring the user back to the main view.

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/gui_newTask.png" width="500">

Pressing _logout_ will bring the user back to the login screen.

## Deleting "tasks"

By pressing _delete mode_ button user is able to delete tasks by pressing _del next to tasks_

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/gui_deleteMode.png" width="500">
