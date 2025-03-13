package oongaliegabangalieBot.commands;

import oongaliegabangalieBot.TaskList;
import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.storage.Storage;
import oongaliegabangalieBot.ui.Ui;

/**
 * Abstract class for all commands.
 */
public abstract class Command {
    /**
     * Executes the command.
     *
     * @param tasks The task list
     * @param ui The UI
     * @param storage The storage
     * @throws botException If there's an error executing the command
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws botException;

    /**
     * Indicates if this command is the exit command.
     *
     * @return true if this is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Helper method to save tasks to storage
     */
    protected void saveTasksToStorage(TaskList tasks, Storage storage) {
        try {
            storage.saveTasks(tasks.getTasksArray());
        } catch (botException e) {
            System.out.println("Warning: Failed to save tasks: " + e.getMessage());
        }
    }
}