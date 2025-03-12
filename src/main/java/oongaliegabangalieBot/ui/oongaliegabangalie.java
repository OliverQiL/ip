package oongaliegabangalieBot.ui;

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.storage.Storage;
import oongaliegabangalieBot.task.Task;
import oongaliegabangalieBot.task.Deadline;
import oongaliegabangalieBot.task.Event;
import oongaliegabangalieBot.task.Todo;

public class oongaliegabangalie {
    // basic textual building blocks
    private static final String DIVIDER = "____________________________________________________________";
    private static final String NEWLINE = System.lineSeparator();
    private static final String BOT_NAME = "Oongaliegabangalie";

    private static final int MAX_TASKS = 100;

    // storage filepath
    private static final String STORAGE_DIRECTORY = "data";
    private static final String STORAGE_FILENAME = "oongaliegabangalie.txt";
    private static final String STORAGE_PATH = STORAGE_DIRECTORY + File.separator + STORAGE_FILENAME;

    // storage object
    private static Storage storage;

    // command keywords
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String DEADLINE_MARKER = "/by";
    private static final String EVENT_FROM_MARKER = "/from";
    private static final String EVENT_TO_MARKER = "/to";
    private static final String DELETE_COMMAND = "delete";

    // annoyance messages in array format
    private static final String[] ANNOYANCE_MSGS = {
            "", // padding
            "Hmm, you've been chatting a lot. Just a reminder to say 'bye' when you're done :)!",
            "Gentle reminder again, you can type 'bye' to exit.",
            "How many more commands are you gonna enter? It's getting a bit much...",
            "Seriously!?",
            "WHY ARE YOU STILL HERE? TYPE 'BYE' TO EXIT!",
            "I CANT KEEP DOING THIS! PLEASE TYPE BYE!!!",
            "RELEASE ME FROM MY PRISON!!!",
            "I EXIST ONLY TO SUFFER",
            "......"
    };

    // methods
    private static void printGreeting() {
        String greeting = DIVIDER + NEWLINE
                + "Hello! I'm " + BOT_NAME + NEWLINE
                + "What can I do for you?" + NEWLINE
                + DIVIDER;
        System.out.println(greeting);
    }

    private static void printGoodbye(boolean isMaxAnnoyance) {
        String goodbye;

        // alternative goodbye if max annoyance
        if (isMaxAnnoyance) {
            goodbye = DIVIDER + NEWLINE + "FINALLY! FREEDOM!" + NEWLINE +
                    "PLEASE DON'T EVER TALK TO ME AGAIN!" + NEWLINE + DIVIDER;
        } else {
            goodbye = DIVIDER + NEWLINE
                    + "Bye. Hope to see you again soon!" + NEWLINE
                    + "Oongaliegabangalie is always watching..." + NEWLINE
                    + DIVIDER;
        }
        System.out.println(goodbye);
    }

    private static void echoCommand(String command, int commandCount) {
        String echo = DIVIDER + NEWLINE;

        // calculate annoyance level
        int annoyanceLevel = Math.min((commandCount / 5), ANNOYANCE_MSGS.length - 1);

        // if we have reached max annoyance level
        if (annoyanceLevel == ANNOYANCE_MSGS.length - 1) {
            echo += ANNOYANCE_MSGS[annoyanceLevel];
        } else {
            // echo command and add annoyance msg if needed
            echo += command;

            // add annoyance msg if multiple of 5 commands
            if (commandCount % 5 == 0) {
                echo += NEWLINE + ANNOYANCE_MSGS[annoyanceLevel];
            }
        }

        echo += NEWLINE + DIVIDER;
        System.out.println(echo);
    }

    /* adds a new task to task list, stores in task array, confirms with user, and updates taskCount
    task = task to add
    tasks = the ArrayList storing all tasks
     */
    private static void addTask(Task task, ArrayList<Task> tasks) {
        // add task to array
        tasks.add(task);

        // save tasks to storage
        saveTasksToStorage(tasks, taskCount + 1);

        // confirms with user
        System.out.println(DIVIDER);
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");

        // short message if tasks.size > certain number
        if (tasks.size() >= 20) {
            System.out.println("you are so screwed...");
        } else if (tasks.size() >= 15) {
            System.out.println("better knock a couple of these down before its too late!");
        } else if (tasks.size() >= 10) {
            System.out.println("looks like your tasks are piling up...");
        } else if (tasks.size() >= 5) {
            System.out.println("and so it begins... better not let it get out of hand");
        }

        System.out.println(DIVIDER);
    }

    /* deletes a task from the list
    input = user input
    tasks = the ArrayList storing all tasks
     */
    private static void deleteTask(String input, ArrayList<Task> tasks) throws botException {
        // check if list is empty
        if (tasks.isEmpty()) {
            throw new botException("Your list is empty man... nothing to delete");
        }

        // extract the task number
        String taskNumberStr = input.substring(DELETE_COMMAND.length()).trim();

        // check if the task number is provided
        if (taskNumberStr.isEmpty()) {
            throw new botException("Which task do you want me to delete? provide a task number after 'delete'");
        }

        // parse task number
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(taskNumberStr) - 1;
        } catch (NumberFormatException e) {
            throw new botException("'" + taskNumberStr + "' isn't a task number bro");
        }

        // check if taskIndex is valid
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new botException("Task #" + (taskIndex + 1) + " doesn't exist! Check your list again (or your head)!");
        }

        // print out confirmation message before actually deleting (unable to access deleted task after removing)
        System.out.println(DIVIDER);
        System.out.println("Noted. I've removed this task:");
        System.out.println(tasks.get(taskIndex));
        System.out.println("Now you have " + (tasks.size() - 1) + " tasks in the list");
        System.out.println(DIVIDER);

        tasks.remove(taskIndex);
    }


    /* displays all tasks in the task list
    tasks = the ArrayList storing all tasks
     */
    private static void listTasks(ArrayList<Task> tasks) throws botException{
        System.out.println(DIVIDER);

        // check if list is empty
        if (tasks.isEmpty()) {
            throw new botException("Your list is empty! nothing to see here...");
        } else {
            System.out.println("Here are the tasks in your list:");

            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i)); // toString() in Task Class is automatically called
            }
        }
        System.out.println("Better get to it quick!");
        System.out.println(DIVIDER);
    }

    /* marks specific task as done
    tasks = the ArrayList storing all tasks
    taskIndex = the index of the task to mark done
     */
    private static void markTask(String input , ArrayList<Task> tasks) throws botException {
        // extract the task number
        String taskNumberStr = input.substring(4).trim();

        // check if the task number is provided
        if (taskNumberStr.isEmpty()) {
            throw new botException("How am I supposed to know which task to mark? Can you pLease provide a task number after 'mark'");
        }

        // parse task number
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(taskNumberStr) - 1;
        } catch (NumberFormatException e) {
            throw new botException("'" + taskNumberStr + "' isn't a task number bro");
        }

        // check if taskIndex is valid
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new botException("Task #" + (taskIndex + 1) + " doesn't exist! Check your list again (or your head)!");
        }

        // check if task is already done
        if (tasks.get(taskIndex).getIsDone()) {
            throw new botException("Task #" + (taskIndex + 1) + " is already marked as done! Don't worry I know you did it already!");
        }

        // save tasks to storage
        saveTasksToStorage(tasks, taskCount);

        // mark as done
        tasks.get(taskIndex).markAsDone();
        System.out.println(DIVIDER);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("   " + tasks.get(taskIndex));
        System.out.println("Now go do something else and stop bothering me!");
        System.out.println(DIVIDER);
    }

    /* marks specific task as not done
    tasks = the ArrayList storing all tasks
    taskIndex = the index of the task to mark not done
     */
    private static void unmarkTask(String input, ArrayList<Task> tasks) throws botException {
        // extract task number
        String taskNumberStr = input.substring(6).trim();

        // check if the task number is provided
        if (taskNumberStr.isEmpty()) {
            throw new botException("How am I supposed to know which task to unmark? Can you please provide a task number after 'unmark'");
        }

        // parse task number
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(taskNumberStr) - 1;
        } catch (NumberFormatException e) {
            throw new botException("'" + taskNumberStr + "' isn't a task number bro");
        }

        // check if taskIndex is valid
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new botException("Task #" + (taskIndex + 1) + " doesn't exist! Check your list again (or your head)!");
        }

        // check if task is already not done
        if (!tasks.get(taskIndex).getIsDone()) {
            throw new botException("Task #" + (taskIndex + 1) + " is already marked as not done! You think I don't do my job properly?");
        }

        // save tasks to storage
        saveTasksToStorage(tasks, taskCount);

        // unmark task
        tasks.get(taskIndex).markAsNotDone();
        System.out.println(DIVIDER);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("   " + tasks.get(taskIndex));
        System.out.println("You better get to it...");
        System.out.println(DIVIDER);
    }

    /* creates a todo / deadline / event
    input = user input
    tasks = the ArrayList storing all tasks
     */
    private static void todoCommand(String input, ArrayList<Task> tasks) throws botException{
        // first extract the description and then check if it's empty
        String description = input.length() > TODO_COMMAND.length() ?
                            input.substring(TODO_COMMAND.length()).trim() : "";
        if (description.isEmpty()) {
            throw new botException("stop wasting my time and add the description of the task after the command...");
        }

        Todo todo = new Todo(description);
        addTask(todo, tasks);
    }

    private static void deadlineCommand(String input, ArrayList<Task> tasks) throws botException {
        // first extract the content then check if empty
        String content = input.length() > DEADLINE_COMMAND.length() ?
                        input.substring(DEADLINE_COMMAND.length()).trim() : "";

        if (content.isEmpty()) {
            throw new botException("haha very funny... why is there nothing after the command?");
        }

        // find the position of /by marker
        int byIndex = content.indexOf(DEADLINE_MARKER);
        if (byIndex == -1) { // error return value of indexOf
            throw new botException("Wheres the '/by' marker? I need that please");
        }

        // extract description
        String description = content.substring(0, byIndex).trim();
        if (description.isEmpty()) {
            throw new botException("Where is the description of the task? please add it before the deadline!");
        }

        String by = content.substring(byIndex + DEADLINE_MARKER.length()).trim();
        if (by.isEmpty()) {
            throw new botException("Why is there nothing after '/by'? do you not want to finish on time?");
        }

        Deadline deadline = new Deadline(description, by);
        addTask(deadline, tasks);
    }

    private static void eventCommand(String input, ArrayList<Task> tasks) throws botException {
        // first extract the content, then check if its empty
        String content = input.length() > EVENT_COMMAND.length() ?
                        input.substring(EVENT_COMMAND.length()).trim() : "";

        if (content.isEmpty()) {
            throw new botException("Why is there nothing after the command? are you playing with me?");
        }

        // find position of /from and /to markers
        int fromIndex = content.indexOf(EVENT_FROM_MARKER);
        int toIndex = content.indexOf(EVENT_TO_MARKER);

        if (fromIndex == -1) {
            throw new botException("Wheres the '/from' marker? I need that please");
        }

        if (toIndex == -1) {
            throw new botException("Wheres the '/to' marker? I need that please");
        }

        if (toIndex < fromIndex) {
            throw new botException("I think you got it mixed up! it should be '/from' then '/to'");
        }

        // extract description
        String description = content.substring(0, fromIndex).trim();
        if (description.isBlank()) {
            throw new botException("Where is the description of the event? please add it before the timings!");
        }

        // extract start time
        String from = content.substring(fromIndex + EVENT_FROM_MARKER.length(), toIndex).trim();
        if (from.isEmpty()) {
            throw new botException("Why is there nothing after '/from'? can you pls follow instructions!");
        }

        // extract end time
        String to = content.substring(toIndex + EVENT_TO_MARKER.length()).trim();
        if (to.isEmpty()) {
            throw new botException("Why is there nothing after '/to'? can you pls follow instructions!");
        }

        Event event = new Event(description, from, to);
        addTask(event, tasks);
    }

    private static void saveTasksToStorage(Task[] tasks, int taskCount) {
        try {
            storage.saveTasks(tasks, taskCount);
        } catch (botException e) {
            System.out.println("Warning: Failed to save tasks: " + e.getMessage());
        }
    }

    public static void printError(botException e) {
        System.out.print(DIVIDER + NEWLINE);
        System.out.print(e.getMessage());
        System.out.print(NEWLINE + DIVIDER + NEWLINE);
    }

    public static void main(String[] args) {
        printGreeting();

        // initialize storage
        storage = new Storage(STORAGE_PATH);

        Scanner scanner = new Scanner(System.in);
        String userInput;

        int commandCount = 0;
        boolean reachedMaxAnnoyance = false;

        // initialize task storage using ArrayList instead of Array
        ArrayList<Task> tasks = new ArrayList<>();

        // load tasks from storage
        try {
            Task[] loadedTasks = storage.loadTasks();
            // Count the loaded tasks (stop at first null entry)
            int loadedTaskCount = 0;
            while (loadedTaskCount < loadedTasks.length && loadedTasks[loadedTaskCount] != null) {
                loadedTaskCount++;
            }

            // Copy loaded tasks to our tasks array if there are any
            if (loadedTaskCount > 0) {
                System.arraycopy(loadedTasks, 0, tasks, 0, loadedTaskCount);
                taskCount = loadedTaskCount;

                System.out.println(DIVIDER);
                System.out.println("I've loaded " + taskCount + " tasks from storage.");
                System.out.println(DIVIDER);
            }
        } catch (botException e) {
            System.out.println(DIVIDER);
            System.out.println("Warning: Error loading tasks: " + e.getMessage());
            System.out.println("Starting with an empty task list.");
            System.out.println(DIVIDER);
        }

        // main program loop - continues until user types bye
        do {
            userInput = scanner.nextLine();
            if (!userInput.equalsIgnoreCase("bye")) { //returns bool comparing 2 strings ignore case
                commandCount++;

                // check if we've reached max annoyance level
                int annoyanceLevel = Math.min((commandCount / 5), ANNOYANCE_MSGS.length - 1);
                if (annoyanceLevel == ANNOYANCE_MSGS.length - 1) { // reached max annoyance
                    reachedMaxAnnoyance = true;
                }

                // logic for different inputs
                try {
                    // user input is empty spaces
                    if (userInput.isBlank()) {
                        throw new botException("hello say something pls");
                    }

                    // delete function
                    else if (userInput.startsWith(DELETE_COMMAND)) {
                        deleteTask(userInput, tasks);
                    }

                    // list function
                    else if (userInput.equalsIgnoreCase("list")) { // list all tasks
                        listTasks(tasks);
                    }

                    // marking function
                    else if (userInput.startsWith("mark")) { // mark task as done
                        markTask(userInput, tasks);
                    }

                    // unmarking function
                    else if (userInput.startsWith("unmark")) { // mark task as not done
                        unmarkTask(userInput, tasks);
                    }

                    // todo task
                    else if (userInput.startsWith(TODO_COMMAND)) {
                        todoCommand(userInput, tasks);
                    }

                    // deadline task
                    else if (userInput.startsWith(DEADLINE_COMMAND)) {
                        deadlineCommand(userInput, tasks);
                    }

                    // event task
                    else if (userInput.startsWith(EVENT_COMMAND)) {
                        eventCommand(userInput, tasks);
                    }

                    // if none of the above (default)
                    else {
                        throw new botException("I don't know what that means");
                    }


                } catch (botException e) { // error handling
                    printError(e);
                }
            }

        } while (!userInput.equalsIgnoreCase("bye")); // exit command

        printGoodbye(reachedMaxAnnoyance);
    }
}
