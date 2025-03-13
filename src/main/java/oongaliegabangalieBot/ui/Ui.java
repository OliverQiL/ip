package oongaliegabangalieBot.ui;

import java.util.ArrayList;
import java.util.Scanner;

import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.task.Task;

/**
 * Handles all user interactions.
 */
public class Ui {
    // Basic textual building blocks
    private static final String DIVIDER = "____________________________________________________________";
    private static final String NEWLINE = System.lineSeparator();
    private static final String BOT_NAME = "Oongaliegabangalie";

    private final Scanner scanner;

    /**
     * Initializes the UI
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Shows the welcome message
     */
    public void showWelcome() {
        String greeting = DIVIDER + NEWLINE
                + "Hello! I'm " + BOT_NAME + NEWLINE
                + "What can I do for you?" + NEWLINE
                + DIVIDER;
        System.out.println(greeting);
    }

    /**
     * Shows the goodbye message
     */
    public void showGoodbye() {
        String goodbye = DIVIDER + NEWLINE
                + "Bye. Hope to see you again soon!" + NEWLINE
                + "Oongaliegabangalie is always watching..." + NEWLINE
                + DIVIDER;

        System.out.println(goodbye);
    }

    /**
     * Reads a command from the user
     * @return the user's input
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Shows an error message
     */
    public void showError(botException e) {
        System.out.print(DIVIDER + NEWLINE);
        System.out.print(e.getMessage());
        System.out.print(NEWLINE + DIVIDER + NEWLINE);
    }

    /**
     * Shows loading error message
     */
    public void showLoadingError(String errorMessage) {
        System.out.println(DIVIDER);
        System.out.println("Warning: Error loading tasks: " + errorMessage);
        System.out.println("Starting with an empty task list.");
        System.out.println(DIVIDER);
    }

    /**
     * Shows message about loaded tasks
     */
    public void showLoadedTasksMessage(int taskCount) {
        System.out.println(DIVIDER);
        System.out.println("I've loaded " + taskCount + " tasks from storage.");
        System.out.println(DIVIDER);
    }

    /**
     * Shows a message after adding a task
     */
    public void showAddedTask(Task task, int taskCount) {
        System.out.println(DIVIDER);
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");

        // Short message based on task count
        if (taskCount >= 20) {
            System.out.println("you are so screwed...");
        } else if (taskCount >= 15) {
            System.out.println("better knock a couple of these down before its too late!");
        } else if (taskCount >= 10) {
            System.out.println("looks like your tasks are piling up...");
        } else if (taskCount >= 5) {
            System.out.println("and so it begins... better not let it get out of hand");
        }

        System.out.println(DIVIDER);
    }

    /**
     * Shows a message after deleting a task
     */
    public void showDeletedTask(Task task, int remainingTasks) {
        System.out.println(DIVIDER);
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        System.out.println("Now you have " + remainingTasks + " tasks in the list");
        System.out.println(DIVIDER);
    }

    /**
     * Shows all tasks in the list
     */
    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println(DIVIDER);
        System.out.println("Here are the tasks in your list:");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }

        System.out.println("Better get to it quick!");
        System.out.println(DIVIDER);
    }

    /**
     * Shows tasks on a specific date
     */
    public void showTasksOnDate(ArrayList<Task> tasks, String dateStr) {
        System.out.println(DIVIDER);

        if (tasks.isEmpty()) {
            System.out.println("No tasks found on " + dateStr + "! you're free! (or I can't read your dates...)");
        } else {
            System.out.println("Here are the tasks on " + dateStr + ":");

            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }

            if (tasks.size() > 2) {
                System.out.println("Busy day ahead! better not procrastinate!");
            } else {
                System.out.println("Not too busy, but you should still get on with it!");
            }
        }

        System.out.println(DIVIDER);
    }

    /**
     * Shows a message after marking a task as done
     */
    public void showMarkedDoneTask(Task task) {
        System.out.println(DIVIDER);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("   " + task);
        System.out.println("Now go do something else and stop bothering me!");
        System.out.println(DIVIDER);
    }

    /**
     * Shows a message after marking a task as not done
     */
    public void showMarkedNotDoneTask(Task task) {
        System.out.println(DIVIDER);
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        System.out.println("You better get to it...");
        System.out.println(DIVIDER);
    }

    /**
     * Shows tasks matching a keyword
     */
    public void showMatchingTasks(ArrayList<Task> tasks, String keyword) {
        System.out.println(DIVIDER);

        if (tasks.isEmpty()) {
            System.out.println("No tasks matched the keyword '" + keyword + "'!");
            System.out.println("Maybe try another search term? Or are you sure you spelled it right?");
        } else {
            System.out.println("Here are the matching tasks in your list:");

            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }

            if (tasks.size() > 3) {
                System.out.println("Wow that's a lot of matches! You sure use '" + keyword + "' a lot!");
            }
        }

        System.out.println(DIVIDER);
    }
}