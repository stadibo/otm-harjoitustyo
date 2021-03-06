# Software requirements specification

## Purpose of the software

The software helps you keep track of your daily routine, including *habits*, *daily activities* (routines) and *tasks* (“todos”). The application may be used by multiple registered users, for whom the application saves personalized data in the form of habit-, “daily”- and todo-lists and progress in a way like a *RPG* (Role-playing-game). By completing tasks and keeping up with habits one can gain experience and health points, and progress in the game.

## Users
There is one type of user role: *normal user*. 

## UI draft

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/UI_darft_p1.jpg" width="750">

The application opens to a login screen, from where you can either login or create a user, in the create user view. After a successful login the dashboard view of the user is shown. The dashboard includes tracked habits, daily tasks and occasional todos, as well as, current stats for user.

## Basic functionality
### Before login
- The user can create a user in the application
  - Username must be unique and at least 2 characters long
- The user can login to the application
  - The login is successful when a correct username is written into the input field
  - If a user matching the entered character sequence doesn’t exist the application a message will display notifying the user of this.
### After login
- User sees their unfinished todos, daily tasks and currently tracked habits
- Can delete habit/task/to-do
- Choose how hard it is to do the habit/task/to-do (bigger reward in game for harder habits)
- User can mark habit success or failure and mark tasks and to-do:s as done
  
  - Experience, and levels, will be awarded for completing tasks
    - added to the users game stats
    - By not completing daily tasks the user will lose experience in their progression
- User can create new habit
  - Habit shown only to this user
  - Habit is created in the “new” view
- User can create new daily task
  - Tasks shown only to this user
  - Task is created in the “new” view
    - Choose which days of the week it will be shown
  - after being set done daily task will appear the next day to be shown
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
- User can view statistics on progress for habits and amount of completed todos and tasks based on category, etc.
- Choose if the habit is negative or positive when creating new habit
- Modify habit/task/to-do
- Add deadline for to-do
- Categories for to-do:s and tasks
- Multiplayer mechanics: quests, raids, etc.
- Password login
- *superuser* could be added with more privileges.

