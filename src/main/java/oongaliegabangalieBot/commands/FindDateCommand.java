package oongaliegabangalieBot.commands;

import oongaliegabangalieBot.TaskList;
import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.storage.Storage;
import oongaliegabangalieBot.task.Deadline;
import oongaliegabangalieBot.task.Event;
import oongaliegabangalieBot.task.Task;
import oongaliegabangalieBot.ui.Ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a command to find tasks by date.
 */
public class FindDateCommand extends Command {
    private final String dateString;
    private final LocalDate targetDate;

    public FindDateCommand(String dateString) {
        this.dateString = dateString;
        // Parse the date string into a LocalDate object
        this.targetDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws botException {
        ArrayList<Task> allTasks = tasks.getAllTasks();
        ArrayList<Task> tasksOnDate = new ArrayList<>();

        // Find all tasks that occur on the target date
        for (Task task : allTasks) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                LocalDateTime deadlineDateTime = deadline.getByDateTime();

                // Skip if deadline doesn't have a valid date
                if (deadlineDateTime == null) {
                    continue;
                }

                // Check if the deadline date matches the target date
                if (deadlineDateTime.toLocalDate().equals(targetDate)) {
                    tasksOnDate.add(task);
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                LocalDateTime fromDateTime = event.getFromDateTime();
                LocalDateTime toDateTime = event.getToDateTime();

                // Skip if event doesn't have valid dates
                if (fromDateTime == null || toDateTime == null) {
                    continue;
                }

                // Check if the event starts or ends on the target date
                // Or if the event spans over the target date
                LocalDate fromDate = fromDateTime.toLocalDate();
                LocalDate toDate = toDateTime.toLocalDate();

                if (fromDate.equals(targetDate) ||
                        toDate.equals(targetDate) ||
                        (targetDate.isAfter(fromDate) && targetDate.isBefore(toDate))) {
                    tasksOnDate.add(task);
                }
            }
        }

        // Show the found tasks
        ui.showTasksOnDate(tasksOnDate, dateString);
    }
}