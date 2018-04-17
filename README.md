# HabitRPG

The idea of HabitRPG is to keep track of habits in fun way. Not only can you tick of completed task and mark a 
habit done for the day, but also gain energy and experience in a RPG connected the tracker. This way your character in game
will mirror you progressing, cultivating good habits and being productive.

## Documentation

[Requirements specification](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/documentation/requirements-specification.md)

[Architecture](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/documentation/architecture.md)

[Time tracking](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/documentation/time-tracking.md)

[How to use text based UI](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/documentation/text_ui_instructions.md)

## Command Line operations

### Testing

Tests are run with the command

```
mvn test
```

A test coverage report is generated by running the command

```
mvn jacoco:report
```

The coverage report can be viewed by opening the file _target/site/jacoco/index.html_ in a web-browser

### Checkstyle

The checks defined in the file [checkstyle.xml](https://github.com/stadibo/otm-harjoitustyo/blob/master/habitRPG/checkstyle.xml) are run with the command

```
 mvn jxr:jxr checkstyle:checkstyle
```

Potential error messages are shown by opening the file _target/site/checkstyle.html_ in a web-browser
