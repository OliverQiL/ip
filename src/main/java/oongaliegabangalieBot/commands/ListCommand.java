package oongaliegabangalieBot.commands;

import java.util.ArrayList;

import oongaliegabangalieBot.TaskList;
import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.storage.Storage;
import oongaliegabangalieBot.task.Task;
import oongaliegabangalieBot.ui.Ui;

/**
 * Represents a command to list all tasks.
 */
public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws botException {
        ArrayList<Task> taskList = tasks.getAllTasks();
        ui.showTaskList(taskList);
    }
}