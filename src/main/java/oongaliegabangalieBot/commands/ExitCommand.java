package oongaliegabangalieBot.commands;

import oongaliegabangalieBot.TaskList;
import oongaliegabangalieBot.storage.Storage;
import oongaliegabangalieBot.ui.Ui;

/**
 * Represents a command to exit the application.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}