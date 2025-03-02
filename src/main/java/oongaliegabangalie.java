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

    private static void addTask(String task, String[] tasks, int taskCount) {
        String message = DIVIDER + NEWLINE + "added: " + task + NEWLINE + DIVIDER;
        System.out.println(message);
    }

    private static void listTasks(String[] tasks, int taskCount) {
        System.out.println(DIVIDER);

        for (int i = 0; i < taskCount; i++) {
            System.out.println((i +1) + ". " + tasks[i]);
        }

        System.out.println(DIVIDER);
    }

    public static void main(String[] args) {
        printGreeting();

        Scanner scanner = new Scanner(System.in);
        String userInput;

        int commandCount = 0;
        boolean reachedMaxAnnoyance = false;

        String[] tasks = new String[MAX_TASKS];
        int taskCount = 0;

        do {
            userInput = scanner.nextLine();
            if (!userInput.equalsIgnoreCase("bye")) { //returns bool comparing 2 strings ignore case
                commandCount++;

                int annoyanceLevel = Math.min((commandCount / 5), ANNOYANCE_MSGS.length - 1);
                if (annoyanceLevel == ANNOYANCE_MSGS.length - 1) { // reached max annoyance
                    reachedMaxAnnoyance = true;
                }

                if (userInput.equalsIgnoreCase("list")) {
                    listTasks(tasks, taskCount);
                } else {
                    tasks[taskCount] = userInput;
                    addTask(userInput, tasks, taskCount);
                    taskCount++;
                }
            }
        } while (!userInput.equalsIgnoreCase("bye"));

        printGoodbye(reachedMaxAnnoyance);
    }
}
