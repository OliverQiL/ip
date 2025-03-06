import java.util.Scanner;

public class oongaliegabangalie {
    // basic textual building blocks
    private static final String DIVIDER = "____________________________________________________________";
    private static final String NEWLINE = System.lineSeparator();
    private static final String BOT_NAME = "Oongaliegabangalie";

    private static final int MAX_TASKS = 100;

    // command keywords
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String DEADLINE_MARKER = "/by";
    private static final String EVENT_FROM_MARKER = "/from";
    private static final String EVENT_TO_MARKER = "/to";

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
    tasks = the array storing all tasks
    taskCount = the current count of tasks in the array
     */
    private static int addTask(Task task, Task[] tasks, int taskCount) throws botException{
        // check if we've reached max capacity in list
        if (taskCount >= MAX_TASKS) {
            throw new botException("You're so screwed... number of tasks has reached its limit man");
        }

        // add task to array
        tasks[taskCount] = task;

        // confirms with user
        System.out.println(DIVIDER);
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + (taskCount + 1) + " tasks in the list.");
        System.out.println(DIVIDER);

        // return updated task count
        return taskCount + 1;
    }

    /* displays all tasks in the task list
    tasks = the array storing all tasks
    taskCount = the current count of tasks in the array
     */
    private static void listTasks(Task[] tasks, int taskCount) throws botException{
        System.out.println(DIVIDER);

        // check if list is empty
        if (taskCount == 0) {
            throw new botException("Your list is empty! nothing to see here...");
        } else {
            System.out.print("Here are the tasks in your list:");

            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + ". " + tasks[i]); // toString() in Task Class is automatically called
            }
        }
        System.out.println("Better get to it quick!");
        System.out.println(DIVIDER);
    }

    /* marks specific task as done
    tasks = the array storing all tasks
    taskIndex = the index of the task to mark done
     */
    private static void markTask(String input ,Task[] tasks, int taskCount) throws botException {
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
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new botException("Task #" + (taskIndex + 1) + " doesn't exist! Check your list again (or your head)!");
        }

        // check if task is already done
        if (tasks[taskIndex].isDone) {
            throw new botException("Task #" + (taskIndex + 1) + " is already marked as done! Don't worry I know you did it already!");
        }

        // mark as done
        tasks[taskIndex].markAsDone();
        System.out.println(DIVIDER);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("   " + tasks[taskIndex]);
        System.out.println("Now go do something else and stop bothering me!");
        System.out.println(DIVIDER);
    }

    /* marks specific task as not done
    tasks = the array storing all tasks
    taskIndex = the index of the task to mark not done
     */
    private static void unmarkTask(String input, Task[] tasks, int taskCount) throws botException {
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
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new botException("Task #" + (taskIndex + 1) + " doesn't exist! Check your list again (or your head)!");
        }

        // check if task is already not done
        if (!tasks[taskIndex].isDone) {
            throw new botException("Task #" + (taskIndex + 1) + " is already marked as not done! You think I don't do my job properly?");
        }

        // unmark task
        tasks[taskIndex].markAsNotDone();
        System.out.println(DIVIDER);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("   " + tasks[taskIndex]);
        System.out.println("You better get to it...");
        System.out.println(DIVIDER);
    }

    /* creates a todo / deadline / event
    input = user input
    tasks = the array storing all tasks
    taskCount = the current task count
     */
    private static int todoCommand(String input, Task[] tasks, int taskCount) throws botException{
        // first extract the description and then check if it's empty
        String description = input.length() > TODO_COMMAND.length() ?
                            input.substring(TODO_COMMAND.length()).trim() : "";
        if (description.isEmpty()) {
            throw new botException("stop wasting my time and add the description of the task after the command...");
        }

        Todo todo = new Todo(description);
        return addTask(todo, tasks, taskCount);
    }

    private static int deadlineCommand(String input, Task[] tasks, int taskCount) throws botException {
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
        return addTask(deadline, tasks, taskCount);
    }

    private static int eventCommand(String input, Task[] tasks, int taskCount) throws botException {
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
        return addTask(event, tasks, taskCount);
    }

    public static void printError(botException e) {
        System.out.print(DIVIDER + NEWLINE);
        System.out.print(e.getMessage());
        System.out.print(NEWLINE + DIVIDER + NEWLINE);
    }

    public static void main(String[] args) {
        printGreeting();

        Scanner scanner = new Scanner(System.in);
        String userInput;

        int commandCount = 0;
        boolean reachedMaxAnnoyance = false;

        // initialize task storage with max capacity as defined
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

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

                    // list function
                    else if (userInput.equalsIgnoreCase("list")) { // list all tasks
                        listTasks(tasks, taskCount);
                    }

                    // marking function
                    else if (userInput.startsWith("mark")) { // mark task as done
                        markTask(userInput, tasks, taskCount);
                    }

                    // unmarking function
                    else if (userInput.startsWith("unmark")) { // mark task as not done
                        unmarkTask(userInput, tasks, taskCount);
                    }

                    // todo task
                    else if (userInput.startsWith(TODO_COMMAND)) {
                        taskCount = todoCommand(userInput, tasks, taskCount);
                    }

                    // deadline task
                    else if (userInput.startsWith(DEADLINE_COMMAND)) {
                        taskCount = deadlineCommand(userInput, tasks, taskCount);
                    }

                    // event task
                    else if (userInput.startsWith(EVENT_COMMAND)) {
                        taskCount = eventCommand(userInput, tasks, taskCount);
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
