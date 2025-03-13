package oongaliegabangalieBot.commands;

import oongaliegabangalieBot.TaskList;
import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.storage.Storage;
import oongaliegabangalieBot.task.Task;
import oongaliegabangalieBot.ui.Ui;

/**
 * Represents a command to mark a task as not done.
 */
public class UnmarkCommand extends Command {
    private final int taskNumber;

    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws botException {
        Task unmarkedTask = tasks.markTaskAsNotDone(taskNumber);
        saveTasksToStorage(tasks, storage);
        ui.showMarkedNotDoneTask(unmarkedTask);
    }
}