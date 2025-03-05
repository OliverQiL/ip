public class Deadline extends Task {

    // new variable - due date
    protected String by;

    // constructs a new deadline with description and due date
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
