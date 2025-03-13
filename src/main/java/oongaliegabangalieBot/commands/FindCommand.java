package oongaliegabangalieBot.commands;

import oongaliegabangalieBot.TaskList;
import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.storage.Storage;
import oongaliegabangalieBot.task.Task;
import oongaliegabangalieBot.ui.Ui;

import java.util.ArrayList;

/**
 * Represents a command to find tasks by keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws botException {
        ArrayList<Task> allTasks;

        try {
            allTasks = tasks.getAllTasks();
        } catch (botException e) {
            // If no tasks, show appropriate message
            ui.showError(new botException("Cannot find tasks when your list is empty! Nothing to search through..."));
            return;
        }

        ArrayList<Task> matchingTasks = new ArrayList<>();

        // Find all tasks that contain the keyword (case-insensitive)
        for (Task task : allTasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        // Show the found tasks
        ui.showMatchingTasks(matchingTasks, keyword);
    }
}