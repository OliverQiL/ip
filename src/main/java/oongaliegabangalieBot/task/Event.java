package oongaliegabangalieBot.task;

public class Event extends Task {

    // new variables - start and end time
    protected String from;
    protected String to;

    // constructs a new event with description, start and end times
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
