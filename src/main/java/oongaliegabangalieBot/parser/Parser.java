package oongaliegabangalieBot.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import oongaliegabangalieBot.commands.*;
import oongaliegabangalieBot.exception.botException;

/**
 * Parser handles the parsing of user commands.
 */
public class Parser {
    // Command keywords
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String DELETE_COMMAND = "delete";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String FIND_COMMAND = "find";
    private static final String FIND_DATE_COMMAND = "finddate"; // New command for finding by date
  
    /**
     * Parses a command string and returns a Command object
     * @param input The user's input string
     * @return A Command object
     * @throws botException if the command is invalid
     */
    public Command parseCommand(String input) throws botException {
        input = input.trim();

        // Check for blank input
        if (input.isBlank()) {
            throw new botException("hello say something pls");
        }

        // Parse different command types
        if (input.equalsIgnoreCase("bye")) {
            return new ExitCommand();
        } else if (input.startsWith(TODO_COMMAND)) {
            return parseTodoCommand(input);
        } else if (input.startsWith(DEADLINE_COMMAND)) {
            return parseDeadlineCommand(input);
        } else if (input.startsWith(EVENT_COMMAND)) {
            return parseEventCommand(input);
        } else if (input.startsWith(DELETE_COMMAND)) {
            return parseDeleteCommand(input);
        } else if (input.equalsIgnoreCase(LIST_COMMAND)) {
            return new ListCommand();
        } else if (input.startsWith(MARK_COMMAND)) {
            return parseMarkCommand(input);
        } else if (input.startsWith(UNMARK_COMMAND)) {
            return parseUnmarkCommand(input);
        } else if (input.startsWith(FIND_COMMAND)) {
            return parseFindCommand(input);
        } else if (input.startsWith(FIND_DATE_COMMAND)) {
            return parseFindDateCommand(input);
        } else {
            throw new botException("I don't know what that means");
        }
    }

    /**
     * Parses a todo command
     */
    private Command parseTodoCommand(String input) throws botException {
        String description = input.length() > TODO_COMMAND.length() ?
                input.substring(TODO_COMMAND.length()).trim() : "";

        if (description.isEmpty()) {
            throw new botException("stop wasting my time and add the description of the task after the command...");
        }

        return new TodoCommand(description);
    }

    /**
     * Parses a deadline command
     */
    private Command parseDeadlineCommand(String input) throws botException {
        String content = input.length() > DEADLINE_COMMAND.length() ?
                input.substring(DEADLINE_COMMAND.length()).trim() : "";

        if (content.isEmpty()) {
            throw new botException("haha very funny... why is there nothing after the command?");
        }

        String[] parts = parseDeadlineParts(content);
        return new DeadlineCommand(parts[0], parts[1]);
    }

    /**
     * Parses the parts of a deadline command
     */
    private String[] parseDeadlineParts(String content) throws botException {
        final String DEADLINE_MARKER = "/by";

        int byIndex = content.indexOf(DEADLINE_MARKER);
        if (byIndex == -1) {
            throw new botException("Wheres the '/by' marker? I need that please");
        }

        String description = content.substring(0, byIndex).trim();
        if (description.isEmpty()) {
            throw new botException("Where is the description of the task? please add it before the deadline!");
        }

        String by = content.substring(byIndex + DEADLINE_MARKER.length()).trim();
        if (by.isEmpty()) {
            throw new botException("Why is there nothing after '/by'? do you not want to finish on time?");
        }

        return new String[]{description, by};
    }

    /**
     * Parses a find date command
     */
    private Command parseFindDateCommand(String input) throws botException {
        String dateStr = input.length() > FIND_DATE_COMMAND.length() ?
                input.substring(FIND_DATE_COMMAND.length()).trim() : "";

        if (dateStr.isEmpty()) {
            throw new botException("Please specify a date after 'finddate' (e.g., finddate 2019-12-02)");
        }

        try {
            // Try to parse the date to validate it
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime.parse(dateStr + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            return new FindDateCommand(dateStr);
        } catch (DateTimeParseException e) {
            throw new botException("Invalid date format. Please use yyyy-MM-dd format (e.g., 2019-12-02)");
        }
    }

    /**
     * Parses an event command
     */
    private Command parseEventCommand(String input) throws botException {
        String content = input.length() > EVENT_COMMAND.length() ?
                input.substring(EVENT_COMMAND.length()).trim() : "";

        if (content.isEmpty()) {
            throw new botException("Why is there nothing after the command? are you playing with me?");
        }

        String[] parts = parseEventParts(content);
        return new EventCommand(parts[0], parts[1], parts[2]);
    }

    /**
     * Parses the parts of an event command
     */
    private String[] parseEventParts(String content) throws botException {
        final String EVENT_FROM_MARKER = "/from";
        final String EVENT_TO_MARKER = "/to";

        int fromIndex = content.indexOf(EVENT_FROM_MARKER);
        int toIndex = content.indexOf(EVENT_TO_MARKER);

        if (fromIndex == -1) {
            throw new botException("Wheres the '/from' marker? I need that please");
        }

        if (toIndex == -1) {
            throw new botException("Wheres the '/to' marker? I need that please");
        }

        if (toIndex < fromIndex) {
            throw new botException("I think you got it mixed up! it should be '/from' then '/to'");
        }

        String description = content.substring(0, fromIndex).trim();
        if (description.isBlank()) {
            throw new botException("Where is the description of the event? please add it before the timings!");
        }

        String from = content.substring(fromIndex + EVENT_FROM_MARKER.length(), toIndex).trim();
        if (from.isEmpty()) {
            throw new botException("Why is there nothing after '/from'? can you pls follow instructions!");
        }

        String to = content.substring(toIndex + EVENT_TO_MARKER.length()).trim();
        if (to.isEmpty()) {
            throw new botException("Why is there nothing after '/to'? can you pls follow instructions!");
        }

        return new String[]{description, from, to};
    }

    /**
     * Parses a delete command
     */
    private Command parseDeleteCommand(String input) throws botException {
        String taskNumberStr = input.substring(DELETE_COMMAND.length()).trim();

        if (taskNumberStr.isEmpty()) {
            throw new botException("Which task do you want me to delete? provide a task number after 'delete'");
        }

        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new botException("'" + taskNumberStr + "' isn't a task number bro");
        }
    }

    /**
     * Parses a mark command
     */
    private Command parseMarkCommand(String input) throws botException {
        String taskNumberStr = input.substring(MARK_COMMAND.length()).trim();

        if (taskNumberStr.isEmpty()) {
            throw new botException("How am I supposed to know which task to mark? Can you pLease provide a task number after 'mark'");
        }

        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            return new MarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new botException("'" + taskNumberStr + "' isn't a task number bro");
        }
    }

    /**
     * Parses an unmark command
     */
    private Command parseUnmarkCommand(String input) throws botException {
        String taskNumberStr = input.substring(UNMARK_COMMAND.length()).trim();

        if (taskNumberStr.isEmpty()) {
            throw new botException("How am I supposed to know which task to unmark? Can you please provide a task number after 'unmark'");
        }

        try {
            int taskNumber = Integer.parseInt(taskNumberStr);
            return new UnmarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            throw new botException("'" + taskNumberStr + "' isn't a task number bro");
        }
    }

    /**
     * Parses a find command
     */
    private Command parseFindCommand(String input) throws botException {
        String keyword = input.length() > FIND_COMMAND.length() ?
                input.substring(FIND_COMMAND.length()).trim() : "";

        if (keyword.isEmpty()) {
            throw new botException("How am I supposed to find anything when you don't tell me what to look for? Please provide a keyword after 'find'");
        }

        return new FindCommand(keyword);
    }
}