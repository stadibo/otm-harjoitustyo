# Testing document

The application is tested with both automatic unit- and integration tests using the JUnit library, as well as, manually performed system level tests.

## Unit- and integration testing

### Software logic

The core of the automatic tests consists of integration test for the software logic, that is the tests for "Service" classes in the package [habitrpg.domain](https://github.com/stadibo/otm-harjoitustyo/tree/master/habitRPG/src/main/java/habitrpg/domain), specifically [UserServiceTest](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/test/java/habitrpg/domain/UserServiceTest.java), [TodoServiceTest](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/test/java/habitrpg/domain/TodoServiceTest.java), [HabitServiceTest](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/test/java/habitrpg/domain/HabitServiceTest.java) and [DailyServiceTest](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/test/java/habitrpg/domain/DailyServiceTest.java). The test cases simulate operations the UI could perform with the classes: [UserService](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/UserService.java), [TodoService](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/TodoService.java), [HabitService](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/HabitService.java) and [DailyService](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/DailyService.java).

The integration tests use a temporary database for persistent storage. This is deleted after tests finish.

Simple unit tests have been created to test functionality, like the Equals() method, which integration tests do not cover for the software logic layers' object classes: [User](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/User.java), [Todo](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/Todo.java), [Habit](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/Habit.java) and [Daily](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/Daily.java). The [Time](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/src/main/java/habitrpg/domain/Time.java) class is tested to make sure it parses and gives out dates correctly.

### DAO-classes

Functionality for DAO-classes, as well as the database, has been tested with a temporary database for persistent storage. This is deleted after tests finish.

### Test coverage

Excluding the UI component of the application the tests line coverage is 97% and branch coverage 89%.

<img src="https://raw.githubusercontent.com/stadibo/otm-harjoitustyo/master/habitRPG/documentation/img/test_coverage.png" width="800">

Cases not extensively tested in integration tests was the lack of a database or denied r/w access to database file. DAO-classes were tested with dropping the table for data type, but application has no proper way to communicate this error to user.

## System testing

System testing for software was performed manually

### Installation

The application has been downloaded as per instruction in [User Guide](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/documentation/userGuide.md) on both OSX and in a Linux-environment, and tested in various use cases including: Database file already exists in folder root and contains information; no database exists and application initializes a new database; applcation is running and database file is deleted.

### Functionality

All the required functions specified in [Requirements Specification](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/documentation/requirements-specification.md) and shown in [User Guide](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/documentation/userGuide.md) have been covered. Attempts to fill bad input, such as empty strings, into text fields.

## Unresolved issues

The application does not give very specific error messages in case of large scale errors, like if the database is suddenly deleted.

Not all tests work properly in Windows-environment because of not being able to delete files in use. This does not affect use of the application itself when run from -jar.
