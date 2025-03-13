package oongaliegabangalieBot;

import java.io.File;
import java.util.ArrayList;

import oongaliegabangalieBot.commands.Command;
import oongaliegabangalieBot.commands.ExitCommand;
import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.parser.Parser;
import oongaliegabangalieBot.storage.Storage;
import oongaliegabangalieBot.task.Task;
import oongaliegabangalieBot.ui.Ui;

/**
 * Main class for Oongaliegabangalie Bot
 * Coordinates between the UI, TaskList, Storage, and Parser components
 */
public class oongaliegabangalie {
    // Storage filepath
    private static final String STORAGE_DIRECTORY = "data";
    private static final String STORAGE_FILENAME = "oongaliegabangalie.txt";
    private static final String STORAGE_PATH = STORAGE_DIRECTORY + File.separator + STORAGE_FILENAME;

    // Instance Fields
    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private Parser parser;

    public oongaliegabangalie() {
        this(STORAGE_PATH);
    }

    public oongaliegabangalie(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            ArrayList<Task> loadedTasks = storage.loadTasks();
            tasks = new TaskList(loadedTasks);
            if (!loadedTasks.isEmpty()) {
                ui.showLoadedTasksMessage(loadedTasks.size());
            }
        } catch (botException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Core program logic
     * Ties together other classes and functions
     */
    public void run() {
        ui.showWelcome();

        boolean isExit = false;

        while (!isExit) {
            try {
                String userInput = ui.readCommand();
                try {
                    // Parse the command
                    Command command = parser.parseCommand(userInput);

                    // Execute the command
                    command.execute(tasks, ui, storage);

                    // Check if exit command
                    isExit = command.isExit();
                    if (isExit) {
                        (new ExitCommand()).execute(tasks, ui, storage);
                    }

                } catch (botException e) {
                    ui.showError(e);
                }
            } catch (Exception e) {
                ui.showError(new botException("Unexpected error: " + e.getMessage()));
            }
        }
    }

    public static void main(String[] args) {
        new oongaliegabangalie().run();
    }
}