import java.util.Scanner;

public class oongaliegabangalie {
    // basic textual building blocks
    private static final String DIVIDER = "____________________________________________________________";
    private static final String NEWLINE = System.lineSeparator();
    private static final String BOT_NAME = "Oongaliegabangalie";

    private static final int MAX_TASKS = 100;

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
    private static int addTask(Task task, Task[] tasks, int taskCount) {
        // add task to array
        tasks[taskCount] = task;

        // confirms with userv
        String message = DIVIDER + NEWLINE + "added: " + task.description + NEWLINE + DIVIDER;
        System.out.println(message);

        // return updated task count
        return taskCount + 1;
    }

    /* displays all tasks in the task list
    tasks = the array storing all tasks
    taskCount = the current count of tasks in the array
     */
    private static void listTasks(Task[] tasks, int taskCount) {
        System.out.println(DIVIDER);
        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < taskCount; i++) {
            System.out.println((i +1) + ". " + tasks[i]); // toString() in Task Class is automatically called
        }

        System.out.println(DIVIDER);
    }

    /* marks specific task as done
    tasks = the array storing all tasks
    taskIndex = the index of the task to mark done
     */
    private static void markTask(Task[] tasks, int taskIndex) {
        // check if taskIndex is valid
        if (taskIndex >= 0 && taskIndex < tasks.length && tasks[taskIndex] != null) {
            tasks[taskIndex].markAsDone();
            System.out.println(DIVIDER);
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks[taskIndex]);
            System.out.println(DIVIDER);
        } else {
            System.out.println(DIVIDER);
            System.out.println(" Invalid task number!");
            System.out.println(DIVIDER);
        }
    }

    /* marks specific task as not done
    tasks = the array storing all tasks
    taskIndex = the index of the task to mark not done
     */
    private static void unmarkTask(Task[] tasks, int taskIndex) {
        // check if taskIndex is valid
        if (taskIndex >= 0 && taskIndex < tasks.length && tasks[taskIndex] != null) {
            tasks[taskIndex].markAsNotDone();
            System.out.println(DIVIDER);
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println("   " + tasks[taskIndex]);
            System.out.println(DIVIDER);
        } else {
            System.out.println(DIVIDER);
            System.out.println(" Invalid task number!");
            System.out.println(DIVIDER);
        }
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

                // logic for different command types
                if (userInput.equalsIgnoreCase("list")) { // list all tasks
                    listTasks(tasks, taskCount);
                } else if (userInput.startsWith("mark ")) { // mark task as done
                    try {
                        int taskNumber = Integer.parseInt(userInput.substring(5).trim()); // abstract item number
                        markTask(tasks, taskNumber - 1);
                    } catch (NumberFormatException e) {
                        System.out.println(DIVIDER);
                        System.out.println("Please provide a valid task number");
                        System.out.println(DIVIDER);
                    }
                } else if (userInput.startsWith("unmark ")) { // mark task as not done
                    try {
                        int taskNumber = Integer.parseInt(userInput.substring(7).trim());
                        unmarkTask(tasks, taskNumber - 1);
                    } catch (NumberFormatException e) {
                        System.out.println(DIVIDER);
                        System.out.println("Please provide a valid task number");
                        System.out.println(DIVIDER);
                    }
                } else { // add new task to list
                    Task newTask = new Task(userInput);
                    taskCount = addTask(newTask, tasks, taskCount);
                }
            }

        } while (!userInput.equalsIgnoreCase("bye")); // exit command

        printGoodbye(reachedMaxAnnoyance);
    }
}
