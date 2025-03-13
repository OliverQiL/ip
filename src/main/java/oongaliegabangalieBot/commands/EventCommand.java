package oongaliegabangalieBot.commands;

import oongaliegabangalieBot.TaskList;
import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.storage.Storage;
import oongaliegabangalieBot.task.Event;
import oongaliegabangalieBot.ui.Ui;

/**
 * Represents a command to add an event task.
 */
public class EventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    public EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws botException {
        Event event = new Event(description, from, to);
        tasks.addTask(event);
        saveTasksToStorage(tasks, storage);
        ui.showAddedTask(event, tasks.size());
    }
}