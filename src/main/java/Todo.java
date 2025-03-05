public class Todo extends Task {

    // constructs a new todo task with description
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
