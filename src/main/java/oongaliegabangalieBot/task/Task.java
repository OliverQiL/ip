package oongaliegabangalieBot.task;

public class Task {
    protected String description; // description of task
    protected boolean isDone; // whether task is completed

    // constructor of new task
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    // getter method for isDone
    public boolean getIsDone() {
        return isDone;
    }

    public String getDescription() { return description; }

    // return string either X or space depending on task status
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    // returns string representation of task (status + description)
    // is called automatically when printing task
    public String toString() {
        return "[" + getStatusIcon() + "]" + " " + description;
    }
}
