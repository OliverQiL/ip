package oongaliegabangalieBot.commands;

import oongaliegabangalieBot.TaskList;
import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.storage.Storage;
import oongaliegabangalieBot.task.Todo;
import oongaliegabangalieBot.ui.Ui;

/**
 * Represents a command to add a todo task.
 */
public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws botException {
        Todo todo = new Todo(description);
        tasks.addTask(todo);
        saveTasksToStorage(tasks, storage);
        ui.showAddedTask(todo, tasks.size());
    }
}