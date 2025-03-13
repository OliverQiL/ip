package oongaliegabangalieBot;

import java.util.ArrayList;

import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.task.Task;

/**
 * Manages the list of tasks.
 * Provides methods to add, delete, and manipulate tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty task list
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Constructs a task list with existing tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks != null ? tasks : new ArrayList<>();
    }

    /**
     * Adds a task to the list
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list
     * @param index Task index (1-based)
     * @return The deleted task
     * @throws botException if index is invalid
     */
    public Task deleteTask(int index) throws botException {
        // Check if list is empty
        if (tasks.isEmpty()) {
            throw new botException("Your list is empty man... nothing to delete");
        }

        int taskIndex = index - 1; // Convert to 0-based index

        // Check if taskIndex is valid
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new botException("Task #" + index + " doesn't exist! Check your list again (or your head)!");
        }

        // Get task and remove it
        Task taskToDelete = tasks.get(taskIndex);
        tasks.remove(taskIndex);

        return taskToDelete;
    }

    /**
     * Marks a task as done
     * @param index Task index (1-based)
     * @return The marked task
     * @throws botException if index is invalid
     */
    public Task markTaskAsDone(int index) throws botException {
        int taskIndex = index - 1; // Convert to 0-based index

        // Check if taskIndex is valid
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new botException("Task #" + index + " doesn't exist! Check your list again (or your head)!");
        }

        // Check if task is already done
        Task task = tasks.get(taskIndex);
        if (task.getIsDone()) {
            throw new botException("Task #" + index + " is already marked as done! Don't worry I know you did it already!");
        }

        // Mark as done
        task.markAsDone();
        return task;
    }

    /**
     * Marks a task as not done
     * @param index Task index (1-based)
     * @return The unmarked task
     * @throws botException if index is invalid
     */
    public Task markTaskAsNotDone(int index) throws botException {
        int taskIndex = index - 1; // Convert to 0-based index

        // Check if taskIndex is valid
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw new botException("Task #" + index + " doesn't exist! Check your list again (or your head)!");
        }

        // Check if task is already not done
        Task task = tasks.get(taskIndex);
        if (!task.getIsDone()) {
            throw new botException("Task #" + index + " is already marked as not done! You think I don't do my job properly?");
        }

        // Mark as not done
        task.markAsNotDone();
        return task;
    }

    /**
     * Gets all tasks in the list
     * @return ArrayList of tasks
     * @throws botException if list is empty
     */
    public ArrayList<Task> getAllTasks() throws botException {
        if (tasks.isEmpty()) {
            throw new botException("Your list is empty! nothing to see here...");
        }
        return tasks;
    }

    /**
     * Gets the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Gets the tasks as an ArrayList (for storage)
     */
    public ArrayList<Task> getTasksArray() {
        return tasks;
    }

    /**
     * Checks if the task list is empty
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}