package oongaliegabangalieBot.commands;

import oongaliegabangalieBot.TaskList;
import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.storage.Storage;
import oongaliegabangalieBot.task.Deadline;
import oongaliegabangalieBot.ui.Ui;

/**
 * Represents a command to add a deadline task.
 */
public class DeadlineCommand extends Command {
    private final String description;
    private final String by;

    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws botException {
        Deadline deadline = new Deadline(description, by);
        tasks.addTask(deadline);
        saveTasksToStorage(tasks, storage);
        ui.showAddedTask(deadline, tasks.size());
    }
}