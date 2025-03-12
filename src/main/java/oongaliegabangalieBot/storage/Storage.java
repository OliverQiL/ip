package oongaliegabangalieBot.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import oongaliegabangalieBot.exception.botException;
import oongaliegabangalieBot.task.Deadline;
import oongaliegabangalieBot.task.Event;
import oongaliegabangalieBot.task.Task;
import oongaliegabangalieBot.task.Todo;

public class Storage {
    private final String filePath;
    private final String directoryPath;

    // Constructor initializes the file path
    public Storage(String filePath) {
        this.filePath = filePath;
        // Extract directory path from the file path
        int lastSeparatorIndex = filePath.lastIndexOf(File.separator);
        this.directoryPath = lastSeparatorIndex > 0 ? filePath.substring(0, lastSeparatorIndex) : ".";
    }

    /*
    Saves the tasks to the file
    Format: T | isDone | description
            D | isDone | description | by
            E | isDone | description | from | to
    */
    public void saveTasks(ArrayList<Task> tasks) throws botException {
        try {
            // Create directory if it doesn't exist
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Create file writer (will create file if it doesn't exist)
            FileWriter writer = new FileWriter(filePath);

            // Write each task to the file
            for (Task task : tasks) {
                if (task instanceof Todo) {
                    writer.write("T | " + (task.getIsDone() ? "1" : "0") + " | " + task.getDescription());
                } else if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    writer.write("D | " + (task.getIsDone() ? "1" : "0") + " | " +
                            task.getDescription() + " | " + deadline.getBy());
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    writer.write("E | " + (task.getIsDone() ? "1" : "0") + " | " +
                            task.getDescription() + " | " + event.getFrom() + " | " + event.getTo());
                }
                writer.write(System.lineSeparator()); // Add newline
            }

            writer.close(); // Important to close the file writer!
        } catch (IOException e) {
            throw new botException("Error saving tasks: " + e.getMessage());
        }
    }

    // Loads tasks from the file
    public ArrayList<Task> loadTasks() throws botException {
        ArrayList<Task> tasks = new ArrayList<>();

        // Check if file exists, if not return empty array
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                try {
                    String line = scanner.nextLine();

                    // Skip empty lines
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    // Parse the line
                    String[] parts = line.split(" \\| ", -1); // -1 to keep empty strings

                    // Validate line format
                    if (parts.length < 3) {
                        System.out.println("Warning: Skipping invalid line format: " + line);
                        continue;
                    }

                    char taskType = parts[0].charAt(0);
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];

                    Task task = null;

                    switch (taskType) {
                        case 'T': // Todo
                            task = new Todo(description);
                            break;
                        case 'D': // Deadline
                            if (parts.length < 4) {
                                System.out.println("Warning: Skipping invalid Deadline format: " + line);
                                continue;
                            }
                            task = new Deadline(description, parts[3]);
                            break;
                        case 'E': // Event
                            if (parts.length < 5) {
                                System.out.println("Warning: Skipping invalid Event format: " + line);
                                continue;
                            }
                            task = new Event(description, parts[3], parts[4]);
                            break;
                        default:
                            System.out.println("Warning: Unknown task type: " + taskType);
                            continue;
                    }

                    // Set the task status
                    if (isDone) {
                        task.markAsDone();
                    }

                    tasks.add(task);
                } catch (Exception e) {
                    // Handle corrupted line, print warning and continue
                    System.out.println("Warning: Skipping corrupted line. Error: " + e.getMessage());
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            throw new botException("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }
}