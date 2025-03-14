# OongaliegaBangalie Bot User Guide

Welcome to OongaliegaBangalie Bot! Your friendly (albeit slightly sassy) task management assistant. This guide will help you get started and make the most of this bot's features.

## Table of Contents
- [Introduction](#introduction)
- [Getting Started](#getting-started)
- [Features](#features)
    - [Adding Tasks](#adding-tasks)
        - [Todo Tasks](#todo-tasks)
        - [Deadline Tasks](#deadline-tasks)
        - [Event Tasks](#event-tasks)
    - [Managing Tasks](#managing-tasks)
        - [Listing Tasks](#listing-tasks)
        - [Marking Tasks as Done](#marking-tasks-as-done)
        - [Unmarking Tasks](#unmarking-tasks)
        - [Deleting Tasks](#deleting-tasks)
    - [Finding Tasks](#finding-tasks)
        - [Finding by Keyword](#finding-by-keyword)
        - [Finding by Date](#finding-by-date)
- [Command Summary](#command-summary)
- [Error Messages](#error-messages)

## Introduction

OongaliegaBangalie Bot is a text-based task management application that allows you to keep track of your todos, deadlines, and events. The bot automatically saves your tasks, so you can close and reopen the application without losing your data.

## Getting Started

1. Ensure you have Java 11 or higher installed on your computer
2. Download the latest version of OongaliegaBangalie Bot jar file
3. Run the bot using:
   ```
   java -jar oongaliegabangalie.jar
   ```
4. Start interacting with the bot using commands described in this guide

## Features

### Adding Tasks

OongaliegaBangalie Bot supports three types of tasks:

#### Todo Tasks

Todos are simple tasks with no specific timing attached.

**Format**: `todo DESCRIPTION`

**Example**:
```
todo buy groceries
```

**Expected Output**:
```
____________________________________________________________
Got it. I've added this task:
[T] [ ] buy groceries
Now you have 1 tasks in the list.
____________________________________________________________
```

#### Deadline Tasks

Deadlines are tasks that need to be completed by a specific time.

**Format**: `deadline DESCRIPTION /by DATE_TIME`

The date time can be entered in these formats:
- `yyyy-MM-dd HH:mm` (e.g., 2023-03-15 14:30)
- `d/M/yyyy HHmm` (e.g., 15/3/2023 1430)

**Example**:
```
deadline submit assignment /by 2023-04-01 23:59
```

**Expected Output**:
```
____________________________________________________________
Got it. I've added this task:
[D] [ ] submit assignment (by: Apr 1 2023, 11:59 PM)
Now you have 2 tasks in the list.
____________________________________________________________
```

#### Event Tasks

Events are tasks that take place from one time to another.

**Format**: `event DESCRIPTION /from DATE_TIME /to DATE_TIME`

**Example**:
```
event team meeting /from 2023-04-02 10:00 /to 2023-04-02 12:00
```

**Expected Output**:
```
____________________________________________________________
Got it. I've added this task:
[E] [ ] team meeting (from: Apr 2 2023, 10:00 AM to: Apr 2 2023, 12:00 PM)
Now you have 3 tasks in the list.
____________________________________________________________
```

### Managing Tasks

#### Listing Tasks

To see all your tasks, use the list command.

**Format**: `list`

**Example**:
```
list
```

**Expected Output**:
```
____________________________________________________________
Here are the tasks in your list:
1. [T] [ ] buy groceries
2. [D] [ ] submit assignment (by: Apr 1 2023, 11:59 PM)
3. [E] [ ] team meeting (from: Apr 2 2023, 10:00 AM to: Apr 2 2023, 12:00 PM)
Better get to it quick!
____________________________________________________________
```

#### Marking Tasks as Done

To mark a task as completed, use the mark command with the task number.

**Format**: `mark TASK_NUMBER`

**Example**:
```
mark 1
```

**Expected Output**:
```
____________________________________________________________
Nice! I've marked this task as done:
   [T] [X] buy groceries
Now go do something else and stop bothering me!
____________________________________________________________
```

#### Unmarking Tasks

If you need to change a task back to not done, use the unmark command.

**Format**: `unmark TASK_NUMBER`

**Example**:
```
unmark 1
```

**Expected Output**:
```
____________________________________________________________
OK, I've marked this task as not done yet:
   [T] [ ] buy groceries
You better get to it...
____________________________________________________________
```

#### Deleting Tasks

To remove a task from your list, use the delete command.

**Format**: `delete TASK_NUMBER`

**Example**:
```
delete 3
```

**Expected Output**:
```
____________________________________________________________
Noted. I've removed this task:
[E] [ ] team meeting (from: Apr 2 2023, 10:00 AM to: Apr 2 2023, 12:00 PM)
Now you have 2 tasks in the list
____________________________________________________________
```

### Finding Tasks

#### Finding by Keyword

To find tasks containing a specific keyword, use the find command.

**Format**: `find KEYWORD`

**Example**:
```
find assignment
```

**Expected Output**:
```
____________________________________________________________
Here are the matching tasks in your list:
1.[D] [ ] submit assignment (by: Apr 1 2023, 11:59 PM)
____________________________________________________________
```

#### Finding by Date

To find tasks occurring on a specific date, use the finddate command.

**Format**: `finddate YYYY-MM-DD`

**Example**:
```
finddate 2023-04-01
```

**Expected Output**:
```
____________________________________________________________
Here are the tasks on 2023-04-01:
1. [D] [ ] submit assignment (by: Apr 1 2023, 11:59 PM)
Not too busy, but you should still get on with it!
____________________________________________________________
```

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| Add a todo | `todo DESCRIPTION` | `todo buy groceries` |
| Add a deadline | `deadline DESCRIPTION /by DATE_TIME` | `deadline submit assignment /by 2023-04-01 23:59` |
| Add an event | `event DESCRIPTION /from DATE_TIME /to DATE_TIME` | `event team meeting /from 2023-04-02 10:00 /to 2023-04-02 12:00` |
| List tasks | `list` | `list` |
| Mark as done | `mark TASK_NUMBER` | `mark 1` |
| Mark as not done | `unmark TASK_NUMBER` | `unmark 1` |
| Delete a task | `delete TASK_NUMBER` | `delete 1` |
| Find by keyword | `find KEYWORD` | `find assignment` |
| Find by date | `finddate YYYY-MM-DD` | `finddate 2023-04-01` |
| Exit | `bye` | `bye` |

## Error Messages

OongaliegaBangalie Bot will provide helpful (though sometimes sassy) error messages when commands are incorrectly formatted:

- Todo without description:
  ```
  stop wasting my time and add the description of the task after the command...
  ```

- Deadline without description:
  ```
  haha very funny... why is there nothing after the command?
  ```

- Invalid task number:
  ```
  Task #5 doesn't exist! Check your list again (or your head)!
  ```

- Empty task list:
  ```
  Your list is empty! nothing to see here...
  ```

The bot will automatically save your tasks between sessions, so you don't have to worry about losing your data.