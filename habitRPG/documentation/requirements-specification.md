# Software requirements specification

## Purpose of the software

The software helps you keep track of your daily routine, including *habits*, *daily activities* (routines) and *tasks* (“todos”). The application may be used by multiple registered users, for whom the application saves personalized data in the form of habit-, “daily”- and todo-lists and progress for the accompanying *RPG* (Role-playing-game). By completing tasks and keeping up with habits one can gain experience and health points, and progress in the game.

## Users
In the early stages of development there will only be one type of user role: *normal user*. Later in case the application expands to work in a distributed environment a *superuser* could be added with more privileges.

## UI draft

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/documentation/img/UI_darft_p1.jpg" width="750">

The application opens to a login screen, from where you can either login or create a user, in the create user view. After a successful login the dashboard view of the user is shown. The dashboard includes tracked habits, daily tasks and occasional todos, as well as, current stats for the accompanying game. 

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/documentation/img/UI_draft_p2.jpg" width="750">

From the dashboard there is button to move to the play view, where a text based rpg can be played. The interface options change based on what the player is doing.

## Basic functionality
### Before login
- The user can create a user in the application
  - Username must be unique and at least 2 characters long
- The user can login to the application
  - The login is successful when a correct username is written into the input field
  - If a user matching the entered character sequence doesn’t exist the application a message will display notifying the user of this.
### After login
- User sees their unfinished todos, daily tasks and currently tracked habits
- User can mark habit success or failure for the day and mark tasks and todos as done
  - Todos will disappear the next day
  - Energy and gold will be awarded for completing tasks
    - added to the users game stats
    - By not completing daily tasks the user will lose energy in the game
- User can create new habit
  - Habit shown only to this user
  - Habit is created in the “new” view
- User can create new daily task
  - Tasks shown only to this user
  - Task is created in the “new” view
    - Choose which days of the week it will be shown
- User can log out of the application

## Further development
- Game view
    - Unique to the user
    - Text displays what is currently going on in the game
    - User clicks buttons with descriptions of available actions
    - Energy, experience and gold is displayed at the top
    - Inventory displayed with “inventory” command
    - Random encounters with “enemies” while exploring
- Choose which days of the week the habit will be shown
- Choose how hard it is to do the habit (bigger reward in game for harder habits)
- User can view statistics on progress for habits and amount of completed todos and tasks based on category, etc.
- Choose if the habit is negative or positive when creating new habit
- Modify habit/task/todo
- Delete habit/task/todo
- Add deadline for todo
- Categories for todos and tasks
- Multiplayer mechanics: quests, raids, etc.
- Password login

