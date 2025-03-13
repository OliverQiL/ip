package oongaliegabangalieBot.commands;

import oongaliegabangalieBot.TaskList;
import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.storage.Storage;
import oongaliegabangalieBot.task.Task;
import oongaliegabangalieBot.ui.Ui;

/**
 * Represents a command to delete a task.
 */
public class DeleteCommand extends Command {
    private final int taskNumber;

    public DeleteCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws botException {
        Task deletedTask = tasks.deleteTask(taskNumber);
        saveTasksToStorage(tasks, storage);
        ui.showDeletedTask(deletedTask, tasks.size());
    }
}